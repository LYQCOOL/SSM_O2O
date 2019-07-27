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
import com.swpu.o2o.dao.ShopCategoryDao;
import com.swpu.o2o.entity.ShopCategory;
import com.swpu.o2o.exceptions.ShopOperationException;
import com.swpu.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;

	private static Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

	@Override
	public List<ShopCategory> getShopCategoeyList(ShopCategory shopCategoryCondition) {
		// 定义redis的key前缀
		String key = SCLISTKEY;
		// 定义接收对象
		List<ShopCategory> shopCategoryList = null;
		// 定义jackson数据转换操作类
		ObjectMapper mapper = new ObjectMapper();
		// 拼接出redis的key
		if (shopCategoryCondition == null) {
			// 若查询条件为空，则列出所有首页大类，即parentId为空的店铺类别
			key = key + "_allfirstlevel";
		} else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null
				&& shopCategoryCondition.getParent().getShopCategoryId() != null) {
			//若parentId不为空，则列出所有该parentId下的所有子类别
			key=key+"_parent"+shopCategoryCondition.getParent().getShopCategoryId();

		}
		else if(shopCategoryCondition!=null){
			//列出所有子类别，不管属于哪个类，都列出来
			key=key+"_allsecendlevel";
		}
		//判断key是否存在
		if(!jedisKeys.exists(key)){
			//若不存在，则从数据库中取出相应数据
			shopCategoryList=shopCategoryDao.queryShopCategory(shopCategoryCondition);
			//将相关的实体类集合转换成string，存入redis里面对应的key
			String jsonString;
			try{
				jsonString=mapper.writeValueAsString(shopCategoryList);
			}
			catch(JsonProcessingException e){
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ShopOperationException(e.getMessage());
				
			}
			jedisStrings.set(key, jsonString);

		}
		else{
			//键存在
			String jsonString=jedisStrings.get(key);
			//指定要将值转换为的集合类型
			JavaType javaType=mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
			try {
				//将相关key中的value里的String类型转换为对象的实体类集合
				shopCategoryList=mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ShopOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ShopOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new ShopOperationException(e.getMessage());
			}
		}
		return shopCategoryList;
	}

}
