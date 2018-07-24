package cn.kkl.content.test;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Admin
 * operation step:
 * 1. create connect pool object need host and port parameters
 * 2. get connection from connect pool,which is jedis object
 * 3. operate redis by jedis
 * 4. After operating redis by jedis finishing ,close connection every times.then connect pool recycle resource otherwise 
 *    after the connection is exhausted program cast exception
 * 5. close connection pool before system close
 */   
public class JedisPoolTest {

	private String host="192.168.25.133";
	private int port=6379;
	private JedisPool jedisPool;
	private Jedis jedis;
	
	@Before
	public void init() {
		jedisPool = new JedisPool(host, port);
		jedis = jedisPool.getResource();
	}
	
	@Test
	public void set() {
		jedis.set("jedis-pool-test", "jedis pool test");
		jedis.close();
	}
	
	@Test
	public void get() {
		String string = jedis.get("jedis-pool-test");
		System.out.println(string);
		jedis.close();
	}
	
	@Test
	public void del() {
		jedis.del("jedis-pool-test");
		jedis.close();
	}
	
	@After
	public void destory() {
		jedisPool.close();
	}
}
