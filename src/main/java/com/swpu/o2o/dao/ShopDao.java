package com.swpu.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.swpu.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域Id，owner 
	 * @param shopCondition
	 * @param rowIndex 从第几行开始取
	 * @param pageSize 返回的行数
	 * @return
	 */
	//@Param指定唯一标识
	List <Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	/**
	 * 查询店铺
	 */
	Shop queryByshopId(long shopId);
	/**
	 * 新增店铺
	 * @param shop
	 * @return 
	 */
	int insertShop(Shop shop);
	/**
	 * 更新店铺
	 * @param shop
	 * @return int
	 */
	int updateShop(Shop shop);
	

}
