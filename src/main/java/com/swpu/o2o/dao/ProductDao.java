package com.swpu.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.swpu.o2o.entity.Product;

public interface ProductDao {
	/**
	 * 查询删商品列表
	 * @param productCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(
			@Param("productCondition") Product productCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	/**
	 * 查询商品
	 * @param productId
	 * @return
	 */
	Product queryProductByProductId(long productId);
	/**
	 * 查询数量
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);
	/**
	 * 插入商品
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	/**
	 * 更新商品
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
	/**
	 * 删除商品
	 * @param productId
	 * @param shopId
	 * @return
	 */
	int deleteProduct(@Param("productId") long productId,
			@Param("shopId") long shopId);
	/**
	 * 将商品的商品类别Id置为空
	 * @param productCategoryId
	 * @return
	 */
	int updateProductCategoryToNull(long productCategoryId);
}
