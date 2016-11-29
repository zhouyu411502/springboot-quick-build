package org.springboot.quick.autoconfigure.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableConfigurationProperties(JedisConfigurationProperties.class)// 开启属性注入,通过@autowired注入
@ConditionalOnClass(RedisTemplate.class) // 判断这个类是否在classpath中存在
public class RedisAutoConfiguration {

	@Autowired
	JedisConfigurationProperties config;

	@Bean
	@ConditionalOnMissingBean(RedisTemplate.class)
	public RedisTemplate<?, ?> redisTemplate(@Qualifier("connectionFactory") RedisConnectionFactory connectionFactory) {
		RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setDefaultSerializer(new StringRedisSerializer());
		return redisTemplate;
	}

	@Bean("connectionFactory")
	public RedisConnectionFactory connectionFactory(@Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig) {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
		jedisConnectionFactory.setHostName(config.getHost());
		jedisConnectionFactory.setPort(config.getPort());
		jedisConnectionFactory.setPassword(config.getPasswd());
		return jedisConnectionFactory;
	}

	@Bean(name = "jedisPoolConfig")
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(config.getMaxTotal());
		jedisPoolConfig.setMaxIdle(config.getMaxIdle());
		jedisPoolConfig.setMaxWaitMillis(config.getMaxWaitMillis());
		return jedisPoolConfig;
	}
}
