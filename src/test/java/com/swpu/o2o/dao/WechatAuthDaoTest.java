package com.swpu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.entity.WechatAuth;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WechatAuthDaoTest extends BaseTest {
	@Autowired
	private WechatAuthDao wechatAuthDao;

	@Test
	@Ignore
	public void testAInsertWechatAuth() throws Exception {
		WechatAuth wechatAuth = new WechatAuth();
		wechatAuth.setUserId(1L);
		wechatAuth.setOpenId("dafahizhfdhaih");
		wechatAuth.setCreateTime(new Date());
		int effectedNum = wechatAuthDao.insertWechatAuth(wechatAuth);
		assertEquals(1, effectedNum);
	}

	@Test
	public void testBQueryWechatAuthByOpenId(){
		WechatAuth wechatAuth = wechatAuthDao
				.queryWechatInfoByOpenId("dafahizhfdhaih");
		assertEquals("刘勇七", wechatAuth.getPersonInfo().getName());

	}

}
