package com.swpu.o2o.service;

import java.util.List;

import com.swpu.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	//redis商店类别键
	public final static String SCLISTKEY = "shopcategorylist";
	/**
	 * 根据查询条件获取ShopCategory列表
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoeyList(ShopCategory shopCategoryCondition);

}
