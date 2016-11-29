package org.springboot.quick.autoconfigure.redis.impl;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springboot.quick.autoconfigure.redis.RedisService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * 封装redis 缓存服务器服务接口
 * 
 * @author chababa
 * 
 */
//@Service(value = "redisService")
public class RedisServiceImpl implements RedisService {

	private static String redisCode = "utf-8";
	private RedisTemplate<String, String> redisTemplate;
	
	public RedisServiceImpl(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	/**
	 * @param key
	 */
	public long del(final String... keys) {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long result = 0;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i].getBytes());
				}
				return result;
			}
		});
	}

	/**
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(final byte[] key, final byte[] value, final long liveTime) {
		redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return 1L;
			}
		});
	}

	/**
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(String key, String value, long liveTime) {
		this.set(key.getBytes(), value.getBytes(), liveTime);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		this.set(key, value, 0L);
	}

	/**
	 * @param key
	 * @param value
	 */
	public void set(byte[] key, byte[] value) {
		this.set(key, value, 0L);
	}

	/**
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				try {
					return new String(connection.get(key.getBytes()), redisCode);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return "";
			}
		});
	}
	
	@Override
	public String hget(String key, String hashKey){
		return hashOps().get(key, hashKey);
	}
	
	@Override
	public void hdel(String key, Object... hashKeys){
		hashOps().delete(key, hashKeys);
	}
	

	@Override
	public void hset(String key, String hashKey, String value){
		hashOps().put(key, hashKey, value);
	}
	
	@Override
	public List<String> hmget(String key, Collection<String> hashKeys){
		return hashOps().multiGet(key, hashKeys);
	}
	
	@Override
	public void hmset(String key, Map<String, String> m){
		hashOps().putAll(key, m);
	}
	
	@Override
	public Long sadd(String key, String... values){
		return setOps().add(key, values);
	}
	
	@Override
	public Set<String> smembers(String key){
		return setOps().members(key);
	}
	
	@Override
	public Boolean zadd(String key, String value, double score){
		return zSetOps().add(key, value, score);
	}
	
	@Override
	public Set<String> zrange(String key){
		return zrange(key, 0, -1);
	}

	@Override
	public Set<String> zrange(String key, int start, int end){
		return zSetOps().range(key,start,end);
	}
	
	@Override
	public Long rpush(String key, String value){
		return listOps().rightPush(key, value);
	}
	
	@Override
	public Long rpush(String key, String ... values){
		return listOps().rightPushAll(key, values);
	}
	@Override
	public Long lpush(String key, String value){
		return listOps().leftPush(key, value);
	}
	
	@Override
	public Long lpush(String key, String ... value){
		return listOps().leftPushAll(key, value);
	}
	
	/**
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
	}

	/**
	 * @return
	 */
	public String flushDB() {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	/**
	 * @return
	 */
	public long dbSize() {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	/**
	 * @return
	 */
	public String ping() {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {

				return connection.ping();
			}
		});
	}
	
	public HashOperations<String, String, String> hashOps(){
		return redisTemplate.opsForHash();
	}
	
	public ValueOperations<String, String> valueOps(){
		return redisTemplate.opsForValue();
	}
	
	public ListOperations<String, String> listOps(){
		return redisTemplate.opsForList();
	}
	
	public ZSetOperations<String, String> zSetOps(){
		return redisTemplate.opsForZSet();
	}
	
	public SetOperations<String, String> setOps(){
		return redisTemplate.opsForSet();
	}
}
