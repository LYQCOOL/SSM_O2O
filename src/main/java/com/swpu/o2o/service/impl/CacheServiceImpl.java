package com.swpu.o2o.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swpu.o2o.cache.JedisUtil;
import com.swpu.o2o.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService {
	// 定义操作key的对象
	@Autowired
	private JedisUtil.Keys jedisKeys;

	@Override
	public void removeFromCatche(String keyPrefix) {
		// 匹配获取所有以字符串keyPrefix开头的键的集合
		Set<String> keySet = jedisKeys.keys(keyPrefix + "*");
		for (String key : keySet) {
			// 遍历删除集合中对应键
			jedisKeys.del(key);
		}
	}

}
