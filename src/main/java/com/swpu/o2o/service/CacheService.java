package com.swpu.o2o.service;

public interface CacheService {
	/**
	 * 根据key前缀删除匹配该模式下的所有key-value（如shopcategory，则以该字符串开头的所有键值都会删除）
	 * @param keyPrefix
	 */
	void removeFromCatche(String keyPrefix);

}
