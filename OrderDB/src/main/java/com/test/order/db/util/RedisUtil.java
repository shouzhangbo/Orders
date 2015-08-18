package com.test.order.db.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	private static final Log log = LogFactory.getLog(RedisUtil.class);
	 private static JedisPool jedisPool = null;
	 /**
	     * 获得连接池
	     * @return 
	     */
	    public static JedisPool getPool() {
	        
	        if (CommonUtil.isEmpty(jedisPool)) {
	            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
	            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	            jedisPoolConfig.setMaxActive(500);
	            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
	            jedisPoolConfig.setMaxIdle(5);
	            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
	            jedisPoolConfig.setMaxWait(1000 * 100);
	            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	            jedisPoolConfig.setTestOnBorrow(true);
	            jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
	        }

	        return jedisPool;
	    }
	    /**
	     * 返还到连接池
	     * @param jedisPool
	     * @param jedis 
	     */
	    public static void returnResource(JedisPool jedisPool, Jedis jedis) {
	        
	        if(!CommonUtil.isEmpty(jedis)) {
	            jedisPool.returnResource(jedis);
	        }
	    }
	    
	    /**
	     * 获取数据
	     * @param key
	     * @return 
	     */
	    public static String get(String key){
	        String value = null;
	        JedisPool jedisPool = null;
	        Jedis jedis = null;
	        
	        try {
	            
	            jedisPool = getPool();
	            jedis = jedisPool.getResource();
	            
	            value = jedis.get(key);
	            
	        } catch (Exception ex) {
	            log.error("取redis值出现异常", ex);
	            jedisPool.returnBrokenResource(jedis);
	        } finally {
	            returnResource(jedisPool, jedis);
	        }
	        
	        return value;
	    }
	    
	    /**
	     * 设置key,value
	     * @param key
	     * @param value 
	     */
	    public static void set(String key, String value) {
	        JedisPool jedisPool = null;
	        Jedis jedis = null;
	        
	        try {
	            
	            jedisPool = getPool();
	            jedis = jedisPool.getResource();
	            
	            jedis.set(key, value);
	            
	        } catch (Exception ex) {
	            log.error("set出现异常", ex);
	            jedisPool.returnBrokenResource(jedis);
	        } finally {
	            returnResource(jedisPool, jedis);
	        }
	    }
	    public static void main(String[] args) {
	    	RedisUtil.set("test", "12345");
			System.out.println(RedisUtil.get("test"));
		}
}
