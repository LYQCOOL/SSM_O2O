package com.swpu.o2o.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.swpu.o2o.dto.ProductCategoryExecution;
import com.swpu.o2o.entity.ProductCategory;
import com.swpu.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	/**
	 * 查询商品列表
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getproductCategoryList(long shopId);
	/**
	 * 批量添加商品类别
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)throws ProductCategoryOperationException;
	/**
	 * 删除商品类别
	 * @param productVategoryId
	 * @param shopId
	 * @return
	 */
	ProductCategoryExecution deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId")long shopId) throws ProductCategoryOperationException;
}
