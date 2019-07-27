package com.swpu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.entity.LocalAuth;
import com.swpu.o2o.entity.PersonInfo;

public class LocalAuthDaoTest extends BaseTest {
	@Autowired
	private LocalAuthDao localAuthDao;

	@Test
	@Ignore
	public void TestAqueryUserByuserNameAndPwd() throws Exception {
		//测试根据用户姓名和密码查询
		LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd("HA", "111111");
		System.out.println(localAuth.getUserName());
	}

	@Test
	@Ignore
	public void TestBqueryUserById() throws Exception{
		//测试根据Id查询
		LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
		System.out.println(localAuth.getUserName());

	}

	@Test
	@Ignore
	public void TestCInsertUser() throws Exception{
		//测试插入数据
		LocalAuth localAuth = new LocalAuth();
		localAuth.setUserName("test");
		localAuth.setPassword("222222");
		localAuth.setCreateTime(new Date());
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		localAuth.setPersonInfo(personInfo);
		int effectedNum = localAuthDao.insertLocalAuth(localAuth);
		assertEquals(effectedNum, 2);
	}
	@Test
	public void TestDUpdateuser() throws Exception{
		
		//测试更新
		Date now=new Date();
		int effectedNum=localAuthDao.updateLocalAuth(1, "test","222222","123141431", now);
		assertEquals(effectedNum, 1);
	}
}
