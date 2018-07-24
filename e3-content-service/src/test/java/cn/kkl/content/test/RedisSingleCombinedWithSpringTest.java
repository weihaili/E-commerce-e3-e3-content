package cn.kkl.content.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.kkl.mall.service.JedisClient;

public class RedisSingleCombinedWithSpringTest {
	
	private ClassPathXmlApplicationContext applicationContext;
	private JedisClient jedisClient;

	@Before
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		jedisClient = applicationContext.getBean(JedisClient.class);
	}
	
	@Test
	public void add() {
		jedisClient.set("test04", "jedisCombinedWithSpringTest");
	}
	
	@Test
	public void get() {
		String string = jedisClient.get("test04");
		System.out.println(string);
	}
	
	@Test
	public void del() {
		jedisClient.del("test04");
	}
	
}
