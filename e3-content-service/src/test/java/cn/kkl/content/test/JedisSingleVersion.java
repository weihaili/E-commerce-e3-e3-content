package cn.kkl.content.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * @author Admin
 * operation step:
 * 1. create connect jedis object need host and port parameters
 * 2. operate redis directly by jedis.Every command of redis correspond jedis an method
 * 3. After operating redis finishing,close connection 
 */
public class JedisSingleVersion {
	
	private String host="192.168.25.133";
	private int port=6379;
	private Jedis jedis;
	
	@Before
	public void init() {
		jedis = new Jedis(host, port);
	}
	
	@Test
	public void set() {
		jedis.set("test-jedis-set", "test jedis single version");
		
	}
	
	@Test
	public void get() {
		String string = jedis.get("test-jedis-set");
		System.out.println(string);
	}
	
	@Test
	public void del() {
		jedis.del("test-jedis-set");
	}
	
	@After
	public void destory() {
		jedis.close();
	}

}
