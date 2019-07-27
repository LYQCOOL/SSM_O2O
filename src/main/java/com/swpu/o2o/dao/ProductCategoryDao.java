package com.swpu.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.swpu.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	List<ProductCategory> queryProductCategoryList(long shopId);
	/**
	 * 批量新增商品类别
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	/**
	 * 删除商品类别
	 * @param productVategoryId
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId") long productVategoryId,@Param("shopId")long shopId);

}
