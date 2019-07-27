package com.swpu.o2o.dao;

import java.util.List;

import com.swpu.o2o.entity.ProductImg;

public interface ProductImgDao {
	/**
	 * 查询商品图片列表
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(long productId);
	/**
	 * 批量添加商品图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	/**
	 * 
	 * @param productId
	 * @return
	 */
	int deleteProductByProductId(long productId);
}
