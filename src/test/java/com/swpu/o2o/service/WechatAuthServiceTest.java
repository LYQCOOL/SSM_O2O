package com.swpu.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.dto.WechatExecution;
import com.swpu.o2o.entity.PersonInfo;
import com.swpu.o2o.entity.WechatAuth;
import com.swpu.o2o.enums.WechatAuthStateEnum;

public class WechatAuthServiceTest extends BaseTest{
	@Autowired
	private WechatAuthService wechatAuthService;
	
	/**
	 * 
	 */
	@Test
	public void testRegister(){
		//新增微信账号
		WechatAuth wechatAuth=new WechatAuth();
		PersonInfo personInfo=new PersonInfo();
		String openId="test111";
		//给微信账号设置上用户信息，但不设置用户Id
		//创建微信信息时自动创建用户信息
		personInfo.setCreateTime(new Date());
		personInfo.setEnableStatus(1);
		personInfo.setName("测试");
		personInfo.setUserType(1);
		wechatAuth.setPersonInfo(personInfo);
		wechatAuth.setOpenId(openId);
		wechatAuth.setCreateTime(new Date());
		WechatExecution wechatExecution=wechatAuthService.register(wechatAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(),wechatExecution.getState());
		wechatAuth=wechatAuthService.getWechatAuthByOpenId(openId);
		System.out.println(wechatAuth.getPersonInfo().getName());
		
	}

}
