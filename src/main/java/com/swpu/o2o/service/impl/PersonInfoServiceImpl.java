package com.swpu.o2o.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swpu.o2o.dao.PersonInfoDao;
import com.swpu.o2o.entity.PersonInfo;
import com.swpu.o2o.service.PersonInfoService;

@Service
public class PersonInfoServiceImpl implements PersonInfoService{
	@Autowired
	private PersonInfoDao personInfoDao;
	@Override
	public PersonInfo getPersonInfoById(Long userId) {
		// TODO Auto-generated method stub
		return personInfoDao.queryPersonInfoById(userId);
	}

}
