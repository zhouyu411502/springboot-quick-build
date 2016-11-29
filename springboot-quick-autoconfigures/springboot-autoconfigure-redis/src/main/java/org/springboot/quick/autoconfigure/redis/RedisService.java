package org.springboot.quick.autoconfigure.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis的操作封装接口
 * 
 * @author chababa
 *
 */
public interface RedisService {

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public abstract long del(String... keys);

	/**
	 * 添加key value 并且设置存活时间(byte)
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public abstract void set(byte[] key, byte[] value, long liveTime);

	/**
	 * 添加key value 并且设置存活时间
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 *            单位秒
	 */
	public abstract void set(String key, String value, long liveTime);

	/**
	 * 添加key value
	 * 
	 * @param key
	 * @param value
	 */
	public abstract void set(String key, String value);

	/**
	 * 添加key value (字节)(序列化)
	 * 
	 * @param key
	 * @param value
	 */
	public abstract void set(byte[] key, byte[] value);

	/**
	 * 获取redis value (String)
	 * 
	 * @param key
	 * @return
	 */
	public abstract String get(String key);
	/**
	 * 获取map中指定的key值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public String hget(String key, String hashKey);
	/**
	 * 设置map的key value
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public void hset(String key, String hashKey, String value);
	/**
	 * 批量获取map中指定的keys
	 * @param key
	 * @param hashKeys
	 * @return
	 */
	public List<String> hmget(String key, Collection<String> hashKeys);
	/**
	 * 批量设置map的key value
	 * @param key
	 * @param m
	 */
	public void hmset(String key, Map<String, String> m);

	/**
	 * 通过正则匹配keys
	 * 
	 * @param pattern
	 * @return
	 */
	public abstract Set<?> keys(String pattern);

	/**
	 * 检查key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	public abstract boolean exists(String key);

	/**
	 * 清空redis 所有数据
	 * 
	 * @return
	 */
	public abstract String flushDB();

	/**
	 * 查看redis里有多少数据
	 */
	public abstract long dbSize();

	/**
	 * 检查是否连接成功
	 * 
	 * @return
	 */
	public abstract String ping();
	/**
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	Long lpush(String key, String ... values);

	Long rpush(String key, String ... values);

	Long lpush(String key, String value);

	Long rpush(String key, String value);

	Set<String> zrange(String key, int start, int end);

	Set<String> zrange(String key);

	Long sadd(String key, String... values);

	Set<String> smembers(String key);

	Boolean zadd(String key, String value, double score);

	/** 
	 * hdel:hashmap del. <br/> 
	 * 
	 * @author chababa 
	 * @param key
	 * @param hashKeys 
	 * @since JDK 1.7
	 */  
	void hdel(String key, Object... hashKeys);
}
