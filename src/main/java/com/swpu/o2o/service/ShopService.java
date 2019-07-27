package com.swpu.o2o.service;

import com.swpu.o2o.dto.ImageHolder;
import com.swpu.o2o.dto.ShopExecution;
import com.swpu.o2o.entity.Shop;
import com.swpu.o2o.exceptions.ShopOperationException;

public interface ShopService {
	// 获取店铺列表
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

	// 根据店铺ID获取店铺信息
	Shop getByShopId(long shopId);

	// ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream,String
	// fileName) throws ShopOperationException;

	ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

	// 输入流无法获取名字，因此要将名字传入
	// ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String
	// fileName) throws ShopOperationException;
	// 重构
	ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

}
