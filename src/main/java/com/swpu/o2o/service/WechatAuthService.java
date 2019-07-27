package com.swpu.o2o.service;

import com.swpu.o2o.dto.WechatExecution;
import com.swpu.o2o.entity.WechatAuth;
import com.swpu.o2o.exceptions.WechatAuthOperationException;

public interface WechatAuthService {
	/**
	 * 通过openId查找平台对应的账号
	 * @param openId
	 * @return
	 */
	WechatAuth getWechatAuthByOpenId(String openId) throws WechatAuthOperationException;
	/**
	 * 注册本平台的微信账号
	 * @param wechatAuth
	 * @return
	 * @throws WechatAuthOperationException
	 */
	
	WechatExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException;

}
