package com.swpu.o2o.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpu.o2o.dao.PersonInfoDao;
import com.swpu.o2o.dao.WechatAuthDao;
import com.swpu.o2o.dto.WechatExecution;
import com.swpu.o2o.entity.PersonInfo;
import com.swpu.o2o.entity.WechatAuth;
import com.swpu.o2o.enums.WechatAuthStateEnum;
import com.swpu.o2o.exceptions.WechatAuthOperationException;
import com.swpu.o2o.service.WechatAuthService;

@Service
public class WechatAuthServiceImpl implements WechatAuthService {
	private static Logger log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);
	@Autowired
	private PersonInfoDao personInfoDao;
	@Autowired
	private WechatAuthDao wechatAuthDao;

	@Override
	public WechatAuth getWechatAuthByOpenId(String openId) throws WechatAuthOperationException{
		// TODO Auto-generated method stub
		return wechatAuthDao.queryWechatInfoByOpenId(openId);
	}

	@Override
	@Transactional
	public WechatExecution register(WechatAuth wechatAuth) throws WechatAuthOperationException {
		// 空值判断
		if(wechatAuth==null || wechatAuth.getOpenId()==null){
			return new WechatExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
			
		}
		try{
			//设置创建时间
			wechatAuth.setCreateTime(new Date());
			//如果微信账号中夹带着用户信息并且用户id为空，则认为该用户第一次使用平台（且通过微信登录）
			//则自动创建用户信息
			if(wechatAuth.getPersonInfo()!=null && wechatAuth.getPersonInfo().getUserId()==null){
				try{
					wechatAuth.getPersonInfo().setCreateTime(new Date());
					wechatAuth.getPersonInfo().setEnableStatus(1);
					PersonInfo personInfo=wechatAuth.getPersonInfo();
					int effectedNum=personInfoDao.insertPersonInfo(personInfo);
					wechatAuth.setUserId(personInfo.getUserId());

					if(effectedNum<=0){
						throw new WechatAuthOperationException("添加用户信息失败");
					}
				}
				catch(Exception e){
					log.error("insertPersonInfo error:"+e.toString());
					throw new WechatAuthOperationException("insertPersonInfo error:"+e.toString());
				}
			}
			//创建属于本平台的微信账号
			int effectedNum=wechatAuthDao.insertWechatAuth(wechatAuth);
			if(effectedNum<=0){
				throw new WechatAuthOperationException("账号创建失败");
			}
			else{
				return new WechatExecution(WechatAuthStateEnum.SUCCESS,wechatAuth);
				
			}
		}
		catch(Exception e){
			log.error("insertWechatAuth error:"+e.toString());
			throw new WechatAuthOperationException("insertWechatAuth error:"+e.getMessage());
		}
	}

}
