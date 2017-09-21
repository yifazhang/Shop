package com.zhangyifa.rest.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

/**
 * Created by zyf on 2017/9/20.
 */
public class JedisTest {

    @Test
    public void testJedisSingle() {
        //创建一个jedis的对象
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //调用jedis对象的方法，方式名称和redis的命令一致
        jedis.set("key1","jedis test");
        String key1 = jedis.get("key1");
        System.out.println(key1);
        //关闭jedis
        jedis.close();
    }

    /**
     * 使用连接池
     */
    @Test
    public void testJedis() {
        //创建jedis连接池
        JedisPool pool = new JedisPool("127.0.0.1", 6379);
        //从连接池中获取Jedis对象
        Jedis jedis = pool.getResource();
        String key1 = jedis.get("key1");
        System.out.println(key1);
        //关闭对象
        jedis.close();
    }

    /**
     * 集群测试
     */
    @Test
    public void testJedisCluster() {
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("127.0.0.1", 7001));
        nodes.add(new HostAndPort("127.0.0.1", 7002));
        nodes.add(new HostAndPort("127.0.0.1", 7003));
        nodes.add(new HostAndPort("127.0.0.1", 7004));
        nodes.add(new HostAndPort("127.0.0.1", 7005));
        nodes.add(new HostAndPort("127.0.0.1", 7006));


        JedisCluster cluster = new JedisCluster(nodes);
        cluster.set("key1", "1000");
        String key1 = cluster.get("key1");
        System.out.println(key1);

        cluster.close();
    }

    /**
     * 单机测试
     */
    @Test
    public void testSpringJedisSingle() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/application-jedis.xml");
        JedisPool pool = (JedisPool) applicationContext.getBean("redisClientOnly");
        Jedis jedis = pool.getResource();
        String key1 = jedis.get("key1");
        System.out.println(key1);
        jedis.close();
        pool.close();

    }

    /**
     * 集群测试
     */
    @Test
    public void testSpringJedisCluster() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/application-jedis.xml");
        JedisCluster cluster = (JedisCluster) applicationContext.getBean("redisClient");
        String key1 = cluster.get("key1");
        System.out.println(key1);
        cluster.close();
    }


}
