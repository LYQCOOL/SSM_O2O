package com.swpu.o2o.dao;

import com.swpu.o2o.entity.PersonInfo;

public interface PersonInfoDao {
	/**
	 * 通过用户Id查询用户
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(long userId);
	/**
	 * 添加新用户
	 * @param personInfo
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);
}
