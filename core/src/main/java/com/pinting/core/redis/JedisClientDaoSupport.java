package com.pinting.core.redis;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.springframework.beans.factory.InitializingBean;

import com.pinting.core.exception.PTException;
import com.pinting.core.redis.sentinel.JedisSentinelSharedPool;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;


/**
 * 与Spring集成
 * 支持多数据源
 * 支持读写分离
 * 以表的形式存储，包含了增删改查
 */
public class JedisClientDaoSupport extends CacheBase implements InitializingBean {

    private int currentServerIndex = 0;

    private static JedisClientDaoSupport instance;

    public static JedisClientDaoSupport getInstance(String subSys) {
        if (instance == null) {
            instance = new JedisClientDaoSupport(subSys);
        }
        return instance;
    }

    /**
     * 初始化连接池
     */

    private JedisClientDaoSupport(String subSys) {
        this.setSubSys(subSys);
    }

    /**
     * 供给spring使用
     */
    public JedisClientDaoSupport() {

    }


    public void changeSubSys(String subSys) {
        this.setSubSys(subSys);
    }

    private Jedis setJedis() {
        int count = 1;
        int retryNum = 5;
        Jedis jedis = null;
        do {
            try {
                jedis = JedisSentinelSharedPool.getSentinelJedis();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("获取redis连接失败，正在重试，第" + count + "次");
                closeJedis(jedis);
            }
            count++;
        } while (jedis == null && count < retryNum);

        if (jedis == null) {
            throw new PTException("通讯超时");
        }

        return jedis;
    }

    @Deprecated
    public JedisClientDaoSupport getJedis(int serverIndex) {
        this.currentServerIndex = serverIndex;
        return this;
    }

    @Deprecated
    public JedisClientDaoSupport getJedis() {
        return getJedis(0);
    }

    private void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


    public boolean lock(String key) {
        Jedis jedis = setJedis();
        try {
            String lockKey = PRE_LOCKKEY + key;
            while (true) {
                try {
                    Thread.sleep(2);
                    long k = jedis.setnx(lockKey, "tmpValue");
                    if (k > 0) {
                        jedis.expire(lockKey, 180);
                        return true;
                    }
                } catch (InterruptedException e) {
                    System.out.println("发生异常释放锁：" + lockKey);
                    jedis.del(lockKey);
                    e.printStackTrace();
                }
            }
        } finally {
            closeJedis(jedis);
        }
    }
    
    /**
     * 锁住的时间可配置
     * @param key redisKey
     * @param seconds 锁住秒数
     * @return
     */
    public boolean lock(String key,Integer seconds) {
        Jedis jedis = setJedis();
        try {
            String lockKey = PRE_LOCKKEY + key;
            while (true) {
                try {
                    Thread.sleep(2);
                    long k = jedis.setnx(lockKey, "tmpValue");
                    if (k > 0) {
                        jedis.expire(lockKey, seconds);
                        return true;
                    }
                } catch (InterruptedException e) {
                    System.out.println("发生异常释放锁：" + lockKey);
                    jedis.del(lockKey);
                    e.printStackTrace();
                }
            }
        } finally {
            closeJedis(jedis);
        }
    }

    public void unlock(String key) {
        Jedis jedis = setJedis();
        try {
            String lockKey = PRE_LOCKKEY + key;
            jedis.del(lockKey);
        } finally {
            closeJedis(jedis);
        }
    }

    public void setString(String value, String lookUpKey) throws Exception {
        setString(value, lookUpKey, -1);
    }

    public void setString(String value, String lookUpKey, int expire) throws Exception {
        Jedis jedis = setJedis();
        try {
            String redisKey = getRecordKey("string", lookUpKey);
            jedis.set(redisKey, value);
            if (!value.equals(jedis.get(redisKey))) {
                throw new Exception("redis存储失败：value:" + value + ",lookUpKey:" + lookUpKey + ",expire:" + expire);
            }
            if (expire > -1 && jedis.exists(redisKey)) {
                jedis.expire(redisKey, expire);
            }
        } finally {
            closeJedis(jedis);
        }
    }

    public String getString(String lookUpKey) throws Exception {
        Jedis jedis = setJedis();
        try {
            String redisKey = getRecordKey("string", lookUpKey);
            return jedis.get(redisKey);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 更新键的过期
     *
     * @param objClassName
     * @param lookUpKey
     * @param second
     */
    public void updateExpireTime(String objClassName, String lookUpKey, int second) {
        Jedis jedis = setJedis();
        try {
            byte[] redisKey = getRecordKey(objClassName, lookUpKey).getBytes();
            if (jedis.exists(redisKey)) {
                jedis.expire(redisKey, second);
            } else {
                throw new RuntimeException("此键不存在 objClassName：" + objClassName + " lookUpKey：" + lookUpKey + "");
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 以序列化的方式存储对象到redis
     * 兼容新增对象和新增对象
     */
    public long addOrUpdateObj(Object obj, String lookUpKey, int expire) {
        String tbName = obj.getClass().getSimpleName();
        return this.addOrUpdateObj(obj, tbName, lookUpKey, expire);
    }

    /**
     * 以序列化的方式存储对象到redis
     * 兼容新增对象和新增对象
     */
    public long addOrUpdateObj(Object obj, String tbName, String lookUpKey, int expire) {
        Jedis jedis = setJedis();
        Transaction tx = jedis.multi();
        try {
            String objName = tbName;
            try {
                byte[] redisKey = getRecordKey(objName, lookUpKey).getBytes();
                byte[] tableKey = getTableKey(objName).getBytes();
                tx.set(redisKey, SerializeUtil.serialize(obj));
                //设置过期时间
                if (expire >= 0)
                    tx.expire(redisKey, expire);
                //登记到表
                tx.lrem(tableKey, 1, redisKey);//表去除旧健,兼容更新和新增
                tx.lpush(tableKey, redisKey);//登记为新健
                tx.exec();
                return 1;
            } catch (Exception e) {
                tx.discard();
                e.printStackTrace();
                return -1;
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * @param t
     * @param lookUpKey
     * @return
     * @Method: updateObj   效率比上面的高点
     * @see com.hundsun.epay.cachebase.CacheBase#updateObj(java.lang.Object, java.lang.String)
     */
    @Override
    public <T> long updateObj(T t, String lookUpKey) {
        Jedis jedis = setJedis();
        try {
            try {
                String objName = t.getClass().getSimpleName();
                byte[] redisKey = getRecordKey(objName, lookUpKey).getBytes();
                jedis.set(redisKey, SerializeUtil.serialize(t));
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 以序列化的方式得到对象，并且重新设置过期时间
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> T getObj(Class<T> t, String lookUpKey, int newExpireSencond) {
        String tbName = t.getSimpleName();
        return this.getObj(t, tbName, lookUpKey, newExpireSencond);
    }

    /**
     * 以序列化的方式得到对象，并且重新设置过期时间
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> T getObj(Class<T> t, String tbName, String lookUpKey, int newExpireSencond) {
        Jedis jedis = setJedis();
        String objName = tbName;
        try {
            byte[] key = getRecordKey(objName, lookUpKey).getBytes();
            byte[] objByte = jedis.get(key);
            try {
                if (objByte != null && objByte.length > 0) {
                    if (newExpireSencond > 0) {
                        jedis.expire(key, newExpireSencond);
                    }
                    return (T) SerializeUtil.unserialize(objByte);
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } finally {
            closeJedis(jedis);
        }
    }


    /**
     * 获取对象，但不重新设置过期时间
     */
    public <T extends Object> T getObj(Class<T> t, String lookUpKey) {
        return getObj(t, lookUpKey, -1);
    }

    /**
     * 获取对象，但不重新设置过期时间
     */
    public <T extends Object> T getObj(Class<T> t, String tbName, String lookUpKey) {
        return getObj(t, tbName, lookUpKey, -1);
    }

    /**
     * 以序列化方式删除对象
     *
     * @param t
     * @param lookUpKey
     * @return
     */
    public <T extends Object> long deleteObj(Class<T> t, String lookUpKey) {
        String tbName = t.getSimpleName();
        return this.deleteObj(t, tbName, lookUpKey);
    }

    /**
     * 以序列化方式删除对象
     *
     * @param t
     * @param tbName
     * @param lookUpKey
     * @return
     */
    public <T extends Object> long deleteObj(Class<T> t, String tbName, String lookUpKey) {
        Jedis jedis = setJedis();
        String objName = tbName;
        try {
            byte[] recordKey = getRecordKey(objName, lookUpKey).getBytes();
            byte[] tableKey = getTableKey(objName).getBytes();
            if (jedis.exists(recordKey)) {
                Transaction tx = jedis.multi();
                try {
                    tx.del(recordKey);
                    tx.lrem(tableKey, 1, recordKey);
                    tx.exec();
                    return 1;
                } catch (Exception e) {
                    tx.discard();
                    e.printStackTrace();
                    return -1;
                }
            }
            return -2;
        } finally {
            closeJedis(jedis);
        }
    }

    public <T extends Object> long deleteAllObjs(Class<T> t) {
        //查询所有的行key
        Jedis jedis = setJedis();
        byte[] tableKey = null;
        List<byte[]> allKey = null;
        try {
            tableKey = getTableKey(t.getSimpleName()).getBytes();
            allKey = jedis.lrange(tableKey, 0, -1);
            if (allKey == null || allKey.size() == 0)
                return -1;
        } finally {
            closeJedis(jedis);
        }
        //建立新的事务连接，删除所有的行key
        jedis = setJedis();
        Transaction tx = jedis.multi();
        try {
            for (int i = 0; i < allKey.size(); i++) {
                if (allKey.get(i).length > 0)
                    tx.del(allKey.get(i));
            }
            tx.del(tableKey);
            tx.exec();
            return 1;
        } catch (Exception e) {
            tx.discard();
            e.printStackTrace();
            return -1;

        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 选择以序列化方式存储的所有对象
     *
     * @param t
     * @return
     * @Method: selectAllSer
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> List<T> getAllObjs(Class<T> t) {
        String objName = t.getSimpleName();
        List<T> list = new ArrayList<T>();
        Jedis jedis = setJedis();
        try {
            byte[] tableKey = getTableKey(objName).getBytes();//获取表key
            if (jedis.exists(tableKey)) {
                List<byte[]> allKey = jedis.lrange(tableKey, 0, -1);//获取所有的key，全名key
                for (int i = 0; i < allKey.size(); i++) {
                    byte[] objByte = jedis.get(allKey.get(i));
                    if (objByte.length > 0) {
                        list.add((T) SerializeUtil.unserialize(objByte));
                    }
                }
            }
            return list;
        } finally {
            closeJedis(jedis);
        }
    }

    @SuppressWarnings("rawtypes")
    private Map GetMapFromObj(Object obj) {
        Map<String, String> tmpMap = new HashMap<String, String>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String key = field.getName();

            String value = "";
            try {
                if (field.get(obj) != null)
                    value = field.get(obj).toString();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
            tmpMap.put(key, value);
        }
        return tmpMap;
    }

    /**
     * 以map的方式存储对象
     *
     * @param obj
     * @param lookUpKey
     * @param expire
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public long addOrUpdateObjOfMap(Object obj, String lookUpKey, int expire) {
        Jedis jedis = setJedis();
        try {
            Map tmpMap = GetMapFromObj(obj);
            Transaction tx = jedis.multi();
            try {
                String objName = obj.getClass().getSimpleName();
                String keyTmp = getRecordKey(objName, lookUpKey);
                tx.hmset(keyTmp, tmpMap);//保存该条记录
                if (expire >= 0)
                    tx.expire(keyTmp, expire);
                tx.lrem(getTableKey(objName), 1, keyTmp);//表去除旧健
                tx.lpush(getTableKey(objName), keyTmp);//登记为新健
                tx.exec();
                return 1;
            } catch (Exception e) {
                tx.discard();
                e.printStackTrace();
                return -3;
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 以<String,String>Map形式保存
     *
     * @param map
     * @param lookUpKey
     * @param expire
     * @return
     */
    public long addObjOfHashMap(Map<String, String> map, String lookUpKey, int expire) {
        Jedis jedis = setJedis();
        try {
            Transaction tx = jedis.multi();
            try {
                String keyTmp = getRecordKey("", lookUpKey);
                tx.hmset(keyTmp, map);//保存该条记录
                if (expire >= 0)
                    tx.expire(keyTmp, expire);
                tx.exec();
                return 1;
            } catch (Exception e) {
                tx.discard();
                e.printStackTrace();
                return -3;
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 根据key获取map
     *
     * @param lookUpKey
     * @return
     */
    public Map getHashMapFromObj(String lookUpKey) {

        Jedis jedis = setJedis();

        try {

            String recordKey = getRecordKey("", lookUpKey);
            Map<String, String> tmpMap = jedis.hgetAll(recordKey);

            return tmpMap;

        } finally {
            closeJedis(jedis);
        }

    }

    /**
     * 删除以<String,String>Map形式保存中的某个字符串
     *
     * @param field
     * @param lookUpKey
     * @return
     */
    public long deleteObjOfHashMap(String field, String lookUpKey) {
        Jedis jedis = setJedis();
        try {
            String recordKey = getRecordKey("", lookUpKey);
            if (jedis.exists(recordKey)) {
                Transaction tx = jedis.multi();
                try {
                    tx.hdel(recordKey, field);
                    tx.exec();
                    return 1;
                } catch (Exception e) {
                    tx.discard();
                    e.printStackTrace();
                    return -1;
                }
            }
            return -2;
        } finally {
            closeJedis(jedis);
        }

    }

    public long addOrUpdateObjOfMap(Object obj, String lookUpKey) {
        return addOrUpdateObjOfMap(obj, lookUpKey, -1);
    }


    /**
     * 以map方式获取对象
     *
     * @param t
     * @param lookUpKey
     * @param newExpireSecond
     * @return
     */
    public <T extends Object> T getObjOfMap(Class<T> t, String lookUpKey, int newExpireSecond) {
        String objName = t.getSimpleName();
        Jedis jedis = setJedis();
        try {
            String redisKey = getRecordKey(objName, lookUpKey);
            if (jedis.exists(redisKey)) {
                T tmpT = t.newInstance();
                Field[] fields = tmpT.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String key = field.getName();
                    List<String> valueList = jedis.hmget(redisKey, key);
                    if (valueList == null || valueList.size() == 0)
                        continue;
                    ConvertUtils.register(new DateLocaleConverter(), Date.class);
                    BeanUtils.setProperty(tmpT, key, valueList.get(0));
                }
                if (newExpireSecond > 0) {
                    jedis.expire(redisKey, newExpireSecond);
                }
                return tmpT;
            }
            return null;
        } catch (InstantiationException e1) {
            e1.printStackTrace();
            return null;
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 获取对象，但不重新设置过期时间
     *
     * @param t
     * @param lookUpKey
     * @return
     */
    public <T extends Object> T getObjOfMap(Class<T> t, String lookUpKey) {
        return getObjOfMap(t, lookUpKey, -1);
    }


    /**
     * 删除以mpa存储的对象
     *
     * @param t
     * @param lookUpKey
     * @return
     * @Method: deleteObjOfMap
     */
    public <T extends Object> long deleteObjOfMap(Class<T> t, String lookUpKey) {
        Jedis jedis = setJedis();
        try {
            String objName = t.getSimpleName();
            String recordKey = getRecordKey(objName, lookUpKey);
            if (jedis.exists(recordKey)) {
                Transaction tx = jedis.multi();
                try {
                    tx.del(recordKey);
                    tx.lrem(getTableKey(objName), 1, recordKey);
                    tx.exec();
                    return 1;
                } catch (Exception e) {
                    tx.discard();
                    e.printStackTrace();
                    return -1;
                }
            }
            return -2;
        } finally {
            closeJedis(jedis);
        }

    }

    public <T extends Object> long deleteAllObjsOfMap(Class<T> t) {
        Jedis jedis = setJedis();
        try {
            String objName = t.getSimpleName();
            String tableKey = getTableKey(objName);
            if (jedis.exists(tableKey)) {
                Transaction tx = jedis.multi();
                try {
                    List<String> allKey = getListOfString(tableKey);

                    for (int i = 0; i < allKey.size(); i++) {
                        String recordKey = getRecordKey(objName, allKey.get(i));
                        tx.del(recordKey);
                    }
                    tx.ltrim(tableKey, -1, 0);
                    tx.exec();
                    return 1;
                } catch (Exception e) {
                    tx.discard();
                    e.printStackTrace();
                    return -1;
                }
            }
            return -2;
        } finally {
            closeJedis(jedis);
        }
    }


    /**
     * 选择以map方式存储的所有对象
     *
     * @param t
     * @return
     */
    public <T extends Object> List<T> getAllObjsOfMap(Class<T> t) {
        String objName = t.getSimpleName().toLowerCase();
        List<T> list = new ArrayList<T>();
        Jedis jedis = setJedis();
        try {
            String tableKey = getTableKey(objName);
            if (jedis.exists(tableKey)) {
                try {
                    List<String> allKey = getListOfString(getOriKey(tableKey));
                    for (int i = 0; i < allKey.size(); i++) {
                        String lookUpkey = getOriKey(allKey.get(i));
                        list.add(getObjOfMap(t, lookUpkey));
                    }
                    return list;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 新增list<T>对象
     *
     * @param lookUpKey
     * @param list
     * @return
     */
    public <T extends Object> long addListOfObj(String lookUpKey, List<T> list) {
        String newKey = getSubSysKey(lookUpKey);
        Jedis jedis = setJedis();
        try {
            if (list.size() <= 0)
                return -1;
            jedis.ltrim(newKey, -1, 0);
            for (int i = list.size() - 1; i >= 0; i--) {
                T t = list.get(i);
                jedis.lpush(newKey.getBytes(), SerializeUtil.serialize(t));
            }
//			for (T t : list) {
//				jedis.lpush(newKey.getBytes(),SerializeUtil.serialize(t));
//			}
            return 1;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 新增list<String>对象
     *
     * @param lookUpKey
     * @param list
     * @return
     */
    public long addListOfString(String lookUpKey, List<String> list) {
        String newKey = getSubSysKey(lookUpKey);
        Jedis jedis = setJedis();
        try {
            if (list.size() <= 0)
                return -1;
            jedis.ltrim(newKey, -1, 0);
            for (String t : list) {
                jedis.lpush(newKey, t.toString());
            }
            return 1;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 以list<String> 方式 获取对象
     *
     * @param lookUpKey
     * @param startIndex 第一个元素下标是0（list的表头），第二个元素下标是1，以此类推
     * @param endIndex   -1表示列表的最后一个元素，-2 是倒数第二个，以此类推。
     * @return
     */
    public List<String> getListOfString(String lookUpKey, int startIndex, int endIndex) {
        Jedis jedis = setJedis();
        try {
            return jedis.lrange(getSubSysKey(lookUpKey), startIndex, endIndex);
        } finally {
            closeJedis(jedis);
        }
    }

    public List<String> getListOfString(String key) {
        Jedis jedis = setJedis();
        try {
            return jedis.lrange(getSubSysKey(key), 0, -1);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 获取key对应list
     * @param key
     * @return
     */
    public List<String> getListOfOriString(String key) {
        Jedis jedis = setJedis();
        try {
            return jedis.lrange(StringUtil.trim(key.toLowerCase()), 0, -1);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 以list<T> 方式 获取对象
     *
     * @param lookUpKey
     * @param startIndex 第一个元素下标是0（list的表头），第二个元素下标是1，以此类推
     * @param endIndex   -1表示列表的最后一个元素，-2 是倒数第二个，以此类推。
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> List<T> getListOfObj(String lookUpKey, int startIndex, int endIndex) {
        String newKey = getSubSysKey(lookUpKey);
        Jedis jedis = setJedis();
        try {
            List<T> retList = new ArrayList<T>();
            List<byte[]> list = jedis.lrange(newKey.getBytes(), startIndex, endIndex);
            for (byte[] li : list) {
                T obj = (T) SerializeUtil.unserialize(li);
                retList.add(obj);
            }
            return retList;
        } finally {
            closeJedis(jedis);
        }
    }

    public <T extends Object> List<T> getListOfObj(String lookUpKey) {
        return getListOfObj(lookUpKey, 0, -1);
    }

    /**
     * @param t
     * @param lookUpKey
     * @return
     * @Method: updateObjOfMap ，效率更高点
     */
    @SuppressWarnings("unchecked")
    public <T extends Object> long updateObjOfMap(T t, String lookUpKey) {
        String objName = t.getClass().getSimpleName();
        Jedis jedis = setJedis();
        @SuppressWarnings("rawtypes")
        Map map = GetMapFromObj(t);
        try {
            String redisKey = getRecordKey(objName, lookUpKey);
            if (jedis.exists(redisKey)) {
                String ret = jedis.hmset(redisKey, map);
                return "OK".equals(ret) ? 1 : 0;
            }
            return -1;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 以键值对方式，更新字段值
     *
     * @param objName
     * @param lookUpKey
     * @param map
     * @return
     */
    public long updateObjOfMap(String objName, String lookUpKey, Map<String, String> map) {
        Jedis jedis = setJedis();
        try {
            String redisKey = getRecordKey(objName, lookUpKey);
            if (jedis.exists(redisKey)) {
                String ret = jedis.hmset(redisKey, map);
                return "OK".equals(ret) ? 1 : 0;
            }
            return -1;
        } finally {
            closeJedis(jedis);
        }
    }

    public <T extends Object> long deleteListOfObj(String lookUpKey) {
        String newKey = getSubSysKey(lookUpKey);
        //建立新的事务连接，删除所有的行key
        Jedis jedis = setJedis();
        try {
            if (jedis.exists(newKey)) {
                Transaction tx = jedis.multi();
                try {
                    tx.del(newKey);
                    tx.exec();
                    return 1;
                } catch (Exception e) {
                    tx.discard();
                    e.printStackTrace();
                    return -1;
                }
            }
            return -2;
        } finally {
            closeJedis(jedis);
        }
    }

    public <T extends Object> long deleteListsOfObj(List<String> lookUpKeys) {
        //建立新的事务连接，删除所有的行key
        Jedis jedis = setJedis();
        try {
            Transaction tx = jedis.multi();
            try {
                for (String lookUpKey : lookUpKeys) {
                    String newKey = getSubSysKey(lookUpKey);
                    tx.del(newKey);
                }
                tx.exec();
                return 1;
            } catch (Exception e) {
                tx.discard();
                e.printStackTrace();
                return -1;
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 模糊删除
     *
     * @param lookUpKey
     * @return
     * @Method: deleteListLikeOfObj
     * @Description:模糊删除对象为list的数据
     */
    public <T extends Object> long deleteListLikeOfObj(String lookUpKey) {
        String newKey = getSubSysKey(lookUpKey);
        newKey += "*";
        //建立新的事务连接，删除所有的行key
        Jedis jedis = setJedis();
        try {
            Set<String> keys = jedis.keys(newKey);
            Transaction tx = jedis.multi();
            try {
                if (keys != null && keys.size() > 0) {
                    tx.del(keys.toArray(new String[keys.size()]));
                    tx.exec();
                    return 1;
                } else {
                    return -2;
                }
            } catch (Exception e) {
                tx.discard();
                e.printStackTrace();
                return -1;
            }
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 模糊删除对象为object的数据
     */
    public <T extends Object> long deleteLikeObj(Class<T> t, String lookUpKey) {
        Jedis jedis = setJedis();
        String objName = t.getSimpleName();
        try {
            byte[] recordKey = getRecordKey(objName, lookUpKey).getBytes();
            byte[] tableKey = getTableKey(objName).getBytes();
            Set<byte[]> keys = jedis.keys(recordKey);
            if (keys != null && keys.size() > 0) {
                Transaction tx = jedis.multi();
                try {
                    for (byte[] key : keys) {
                        tx.del(key);
                        tx.lrem(tableKey, 1, key);
                    }
                    tx.exec();
                    return 1;
                } catch (Exception e) {
                    tx.discard();
                    e.printStackTrace();
                    return -1;
                }
            }
            return -2;
        } finally {
            closeJedis(jedis);
        }
    }

    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }


    //========================================================================================================================


    /**
     * 存储REDIS队列 顺序存储
     * 将一个 value 插入到列表 key 的表尾(最右边)
     *
     * @param key   reids键名
     * @param value 键值
     */
    public void rpush(String key, String value) {

        Jedis jedis = setJedis();
        try {
            try {

                jedis.rpush(key, value);

            } catch (Exception e) {
                e.printStackTrace();

            }
        } finally {
            //返还到连接池
            closeJedis(jedis);

        }
    }


    /**
     * 存储REDIS队列 顺序存储
     * 将一个 value 插入到列表 key 的表头(最左边)
     *
     * @param key   reids键名
     * @param value 键值
     */
    public void lpush(String key, String value) {

        Jedis jedis = setJedis();
        try {
            try {

                jedis.lpush(key, value);

            } catch (Exception e) {
                e.printStackTrace();

            }
        } finally {
            //返还到连接池
            closeJedis(jedis);

        }
    }


    /**
     * 获取队列数据
     *
     * @param key 键名
     * @return
     */
    public List<String> lrange(String key) {
        return lrange(key,0,-1);
    }

    /**
     * 获取队列长度
     *
     * @param key 键名
     * @return
     */
    public Long llen(String key) {

        Jedis jedis = setJedis();
        try {
            return jedis.llen(key);

        } catch (Exception e) {
            //释放redis对象
            e.printStackTrace();
            return 0l;
        } finally {
            //返还到连接池
            closeJedis(jedis);
        }

    }

    /**
     * 根据开始与结束值获取队列数据
     *
     * @param key   键名
     * @param start 开始下标  0 表示列表的第一个元素
     * @param end   结束下标  -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推
     * @return
     */
    public List<String> lrange(String key, int start, int end) {

        List<String> list = null;
        Jedis jedis = setJedis();
        try {
            list = jedis.lrange(key, start, end);

        } catch (Exception e) {
            //释放redis对象
            e.printStackTrace();
        } finally {
            //返还到连接池
            closeJedis(jedis);
        }
        return list;
    }

    /**
     * 移除并获取队列表头数据
     *
     * @param key 键名
     * @return
     */
    public String lpop(String key) {

        String object = null;
        Jedis jedis = setJedis();

        try {
            try {
                object = jedis.lpop(key);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            //返还到连接池
            closeJedis(jedis);
        }
        return object;
    }

    /**
     * 移除并获取队列表尾数据
     *
     * @param key 键名
     * @return
     */
    public String rpop(String key) {

        String object = null;
        Jedis jedis = setJedis();

        try {
            try {
                object = jedis.rpop(key);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            //返还到连接池
            closeJedis(jedis);
        }
        return object;
    }

    /**
     * 根据key 删除count个和value值相等的数据
     * count > 0 : 从表头开始向表尾搜索 ；count < 0 : 从表尾开始向表头搜索 ；count = 0 : 移除表中所有与 value 相等的值。
     * @param key
     * @param value
     * @param count
     * @return 返回值为队列中删除数据的个数
     */
    public long lrem(String key,String value,int count){

        Jedis jedis = setJedis();

        try {
            try {
                return jedis.lrem(key,count,value);

            } catch (Exception e) {
                e.printStackTrace();
                return 0l;
            }
        } finally {
            //返还到连接池
            closeJedis(jedis);
        }
    }

}
