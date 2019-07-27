package com.swpu.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.dto.LocalAuthExecution;
import com.swpu.o2o.entity.LocalAuth;
import com.swpu.o2o.entity.PersonInfo;
import com.swpu.o2o.enums.LocalAuthStateEnum;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceTest extends BaseTest{
	@Autowired
	private LocalAuthService localAuthService;
	@Test
	public void testABindLocalAuth(){
		//新增一条平台账号
		LocalAuth localAuth=new LocalAuth();
		PersonInfo personInfo=new PersonInfo();
		String username="testusername2";
		String password="testpassword";
		//给平台设置上用户信息
		//给用户设置上用户id，标明是与哪个用户绑定
		personInfo.setUserId(1L);
		//给平台账号设置用户信息，表明是与哪个用户绑定
		localAuth.setPersonInfo(personInfo);
		localAuth.setUserName(username);
		localAuth.setPassword(password);
		LocalAuthExecution localAuthExecution=localAuthService.bindLocalAuth(localAuth);
		assertEquals(localAuthExecution.getState(),LocalAuthStateEnum.SUCCESS.getState());
	}
	@Test
	@Ignore
	public void testBModifyUser(){
		//更新用户信息
		long userId=3L;
		String username="testusername";
		String password="testpassword";
		String newpassword="testnewpassword";
		LocalAuth localAuth=localAuthService.getLocalAuthByUserId(userId);
		System.out.println(localAuth.getPassword());
		//更改
		LocalAuthExecution localAuthExecution=localAuthService.modifyLocalAuth(userId, username, password, newpassword, new Date());
		assertEquals(LocalAuthStateEnum.SUCCESS.getState(),localAuthExecution.getState());
		
	}

}
