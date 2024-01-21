package com.dafy.core.helper.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dafy.core.util.ListUtil;
import com.dafy.core.util.StringUtil;

@Intercepts({@Signature(type=Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisInterceptor implements Interceptor{
	
	private final static Logger LOG = LoggerFactory.getLogger(MybatisInterceptor.class);
	
	public static final ThreadLocal<Page> localPage = new ThreadLocal<Page>(); 
	
	public static final String ALIAS = "total";
	
	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation arg0) throws Throwable {
		// TODO Auto-generated method stub
		Object[] args = arg0.getArgs();
		MappedStatement mappedStatement = (MappedStatement) args[0];
		Object parameter = args[1];  //查询传递进来的参数
		BoundSql boundSql = mappedStatement.getBoundSql(parameter); 
		//Page page = new Page();
		Page page = localPage.get();
		if(page != null && page.getCount()){
			String countSql = getCountSql(getSql(boundSql));
			List<Integer> countResult = (List<Integer>) proceed(countSql, boundSql, mappedStatement, arg0, true);
			if(ListUtil.isEmpty(countResult))
				return null;
			int count = countResult.get(0);
			page.setTotalRecords(count);
			//如果查询总数量为0则后续也不查询了
			if(count == 0)
				return null;
			String pageSql = getMysqlPageSql(page,boundSql);
			return proceed(pageSql, boundSql, mappedStatement, arg0, false);
		}
		return arg0.proceed();
	}

	@Override
	public Object plugin(Object arg0) {
		// TODO Auto-generated method stub
		return Plugin.wrap(arg0, this); 
	}

	@Override
	public void setProperties(Properties arg0) {
		// TODO Auto-generated method stub
	}
	
	private Object proceed(String sql, BoundSql boundSql, MappedStatement mappedStatement, Invocation arg0, boolean count){
		BoundSql nbs = copyFromBoundSql(mappedStatement, boundSql, sql);
		MappedStatement nms = copyFromMappedStatement(mappedStatement, new MySqlSource(nbs), count);
		arg0.getArgs()[0] = nms;
		try {
			return arg0.proceed();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.debug("请检查sql,错误sql:{}",sql);
			e.printStackTrace();
		}
		return null;
	}
	
	private String getSql(BoundSql boundSql){
		return boundSql.getSql().trim();
	}
	
	/**
	 * 根据传入的sql处理成查询统计总数量的sql
	 * @param originalSql
	 * @return
	 */
	private String getCountSql(String originalSql){
		return "select count(*) " + ALIAS + " from ("+ originalSql + ") a"; 
	}
	
	/**
	 * 返回分页针对mysql的sql语句
	 * @param page
	 * @param boundSql
	 * @return
	 */
	private String getMysqlPageSql(Page page,BoundSql boundSql){
		StringBuffer sql = new StringBuffer(boundSql.getSql());
		if(sql.indexOf("limit") < 0 ){
			sql.append(" limit ").append(page.getOffset()).append(",").append(page.getLimit());
		}
		return sql.toString();
	}
	

	/** 
	 * 复制MappedStatement对象 
	 */  
	private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource, boolean count) {  
		String id = ms.getId();
		Configuration configuration = ms.getConfiguration();
	    Builder builder = new Builder(configuration,id,newSqlSource,ms.getSqlCommandType());  
	      
	    builder.resource(ms.getResource());  
	    builder.fetchSize(ms.getFetchSize());  
	    builder.statementType(ms.getStatementType());  
	    builder.keyGenerator(ms.getKeyGenerator());  
	    builder.keyProperty(StringUtils.join(ms.getKeyProperties(), ","));  
	    builder.timeout(ms.getTimeout());  
	    builder.parameterMap(ms.getParameterMap());  
	    if(count){
	    	builder.resultMaps(createCountResultMap(configuration, id));  
	    }else{
	    	builder.resultMaps(ms.getResultMaps());  
	    }
	    builder.resultSetType(ms.getResultSetType());  
	    builder.cache(ms.getCache());  
	    builder.flushCacheRequired(ms.isFlushCacheRequired());  
	    builder.useCache(ms.isUseCache());  
	      
	    return builder.build();  
	}  
	
	/**
	 * 如果是总页数统计的则要重新生成resultMap
	 * @param configuration
	 * @param id
	 * @return
	 */
	private List<ResultMap> createCountResultMap(Configuration configuration, String id){
		ResultMap.Builder inlineResultMapBuilder = new ResultMap.Builder(
            configuration,
            id + "-Inline",
            Integer.class,
            new ArrayList<ResultMapping>(),
            null);
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        resultMaps.add(inlineResultMapBuilder.build());
        return resultMaps;
	}
  
	/** 
	 * 复制BoundSql对象 
	 */  
	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {  
	    BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());  
	    for (ParameterMapping mapping : boundSql.getParameterMappings()) {  
	        String prop = mapping.getProperty();  
	        if (boundSql.hasAdditionalParameter(prop)) {  
	            newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));  
	        }  
	    }  
	    return newBoundSql;  
	} 
	
	/**
	 * 设置分页参数
	 * @param pageSize 每页个数
	 * @param pageNo 页码
	 * @param orderStr 排序信息
	 */
	public static void setPage(String limit,String offset){
		localPage.set(new Page(StringUtil.toInt(limit), StringUtil.toInt(offset)));
		LOG.info("分页查询: limit {},offset {}", limit, offset);
	}
	
	public static void setPage(Page page){
		localPage.set(page);
	}
	
	public static Page getPage(){
		Page page = localPage.get();
		if(page != null)
			localPage.remove();
		return page;
	}
	
	public static Page resetPage(int count){
		Page page = localPage.get();
		page.setCount(count);
		return page;
	}
	
	public static void clearPage(){
		localPage.remove();
	}
	
	class MySqlSource implements SqlSource{
		private BoundSql boundSql;
		public MySqlSource(BoundSql boundSql){
			this.boundSql = boundSql;
		}
		@Override
		public BoundSql getBoundSql(Object parameterObject) {
			// TODO Auto-generated method stub
			return this.boundSql;
		}
		
	}

}
