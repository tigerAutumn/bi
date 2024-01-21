package com.pinting.core.redis;

/**
 * 连接池配置
 * @author jackin
 *
 */
public class JedisPoolCfg {
	private int maxActive;
	private int maxIdle;
	private int maxWait;
	private boolean testOnBorrow;
	
	public int getMaxActive()
	{
		return maxActive;
	}
	
	public void setMaxActive(int maxActive)
	{
		this.maxActive = maxActive;
		
	}
	
	public int getMaxIdle()
	{
		return maxIdle;
	}
	
	public void setMaxIdle(int maxIdle)
	{
		this.maxIdle = maxIdle;
	}
	
	public int getMaxWait()
	{
		return maxWait;
	}
	
	public void setMaxWait(int maxWait)
	{
		this.maxWait = maxWait;
		
	}
	public boolean getTestOnBorrow()
	{
		return testOnBorrow;
	}
	
	public void setTestOnBorrow(boolean testOnBorrow)
	{
		this.testOnBorrow = testOnBorrow;
		
	}
	
//	
//	public boolean getTestOnReturn()
//	{
//		return testOnBorrow;
//	}
}
