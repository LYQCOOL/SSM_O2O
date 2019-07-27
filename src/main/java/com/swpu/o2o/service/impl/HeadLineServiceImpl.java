package com.swpu.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swpu.o2o.cache.JedisUtil;
import com.swpu.o2o.dao.HeadLineDao;
import com.swpu.o2o.entity.HeadLine;
import com.swpu.o2o.exceptions.AreaOperationException;
import com.swpu.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService{
	@Autowired
	private HeadLineDao headLineDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired 
	private JedisUtil.Strings jedisStrings;

	private Logger logger=LoggerFactory.getLogger(HeadLineServiceImpl.class);
	
	@Override
	public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
		String key=HEADLISTKEY;
		List<HeadLine> headlist=null;
		//定义jackson数据转换操作类
		ObjectMapper mapper=new ObjectMapper();
		//拼接redis的key（轮播图有两种状态：0【禁用】和1【可用】）
		if(headLineCondition!=null&&headLineCondition.getEnableStatus()!=null){
			key=key+"_"+headLineCondition.getEnableStatus();
		}
		//判断key是否存在
		if(!jedisKeys.exists(key)){
			//不存在，则调用Dao层从mysql取出相应数据
			headlist=headLineDao.queryHeadLine(headLineCondition);
			//将相关实体类集合转换为string，存入redis里面对应的key中
			String jsonString;
			try {
				//转换为字符串
				jsonString = mapper.writeValueAsString(headlist);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
						
		}
		else{
			//该key存在，直接从redis将数据取出
			String jsonString=jedisStrings.get(key);
			//指定要将将string转换为相应的集合类型
			JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
			try {
				//将相关key对应的值string取出来转换为相应的对象的实体类集合
				headlist=mapper.readValue(jsonString, javaType);
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
		return headlist;
	}

}
