package com.tang.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 
 * @author 作者: 稀饭
 * @date 创建时间: 2018年11月29日 下午2:42:02
 * @version 1.0
 * @parameter:
 */
public class TestRedis {
	@Test
	public void testJedisSingle() {
		// 1.设置IP地址和端口
		Jedis jedis = new Jedis("192.168.16.129", 6379);
		// 2.设置数据
		//jedis.set("name", "bruce");
		// 3.获得数据
		String name = jedis.get("name");
		System.out.println("name=" + name);
		jedis.close();
	}
	
	@Test
	public void testJedisPool() {
		// 1.获取连接池配置对象,设置配置项
		JedisPoolConfig config = new JedisPoolConfig();
		// 1.1最大的连接数
		config.setMaxTotal(30);
		// 1.2最大的空闲
		config.setMaxIdle(10);
		// 2.获取连接池
		JedisPool jedisPool = new JedisPool(config, "192.168.16.129", 6379);

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// 4.设置数据e
			jedis.set("name", "天天开心");
			String name = jedis.get("name");
			System.out.println("name=" + name);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
			// 虚拟机关闭的时候，释放资源
			if (jedisPool != null) {
				jedisPool.close();
			}
		}
	}

}
