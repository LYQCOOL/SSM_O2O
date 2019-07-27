package com.swpu.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swpu.o2o.cache.JedisUtil;
import com.swpu.o2o.dao.AreaDao;
import com.swpu.o2o.entity.Area;
import com.swpu.o2o.exceptions.AreaOperationException;
import com.swpu.o2o.service.AreaService;

@Service
@Transactional
public class AreaSeriviceImpl implements AreaService{
	@Autowired
	private AreaDao areaDao;
	@Autowired
	//redis键对象
	private JedisUtil.Keys jedisKeys;
	@Autowired
	//redis字符串对象
	private JedisUtil.Strings jedisStrings;
	
	//日志
	private Logger logger=LoggerFactory.getLogger(AreaSeriviceImpl.class);
	@Override
	public List<Area> getAreaList() {
		String key=AREALISTKEY;
		List<Area> areaList=null;
		ObjectMapper mapper=new ObjectMapper();
		if(!jedisKeys.exists(key)){
			areaList=areaDao.queryArea();
			String jsonString;
			try {
				//转换为字符串
				jsonString = mapper.writeValueAsString(areaList);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
			//设置对应键值（字符串）
			jedisStrings.set(key, jsonString);
			
		}
		else{
			//获取对应值
			String jsonString=jedisStrings.get(key);
			JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				//获取为对应对象
				areaList=mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
		}
		return areaList;
	}
	

}
