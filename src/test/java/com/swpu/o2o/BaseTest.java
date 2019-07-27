package com.swpu.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * 用来配置Spring和junit整合，junit启动时加载springIOC容器
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit Spring配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml","classpath:spring/spring-redis.xml"})
public class BaseTest {

}
