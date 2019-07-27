package com.swpu.o2o.service;

import com.swpu.o2o.entity.PersonInfo;

public interface PersonInfoService {
	/**
	 * 根据用户Id获取用户信息
	 * @param personInfoId
	 * @return
	 */
	PersonInfo getPersonInfoById(Long userId);

}
