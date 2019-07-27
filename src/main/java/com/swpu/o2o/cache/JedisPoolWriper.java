package com.swpu.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 强指定redis的JedisPool接口构造函数，这样才能在centos成功创建jedispool
 * 
 * @author xiangze
 *
 */
public class JedisPoolWriper {
	//连接池对象
	private JedisPool jedisPool;

	public JedisPoolWriper(final JedisPoolConfig poolConfig, final String host,
			final int port) {
		try {
			//通过连接池配置信息，IP，端口构造连接池对象
			jedisPool = new JedisPool(poolConfig, host, port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//获取redis连接池对象
	public JedisPool getJedisPool() {
		return jedisPool;
	}
	//注入redis连接池对象
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}
