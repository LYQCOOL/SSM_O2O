package com.swpu.o2o.service;

import java.util.List;
import com.swpu.o2o.dto.ImageHolder;
import com.swpu.o2o.dto.ProductExecution;
import com.swpu.o2o.entity.Product;
import com.swpu.o2o.exceptions.ProductCategoryOperationException;

public interface ProductService {
	/**
	 * 获取商品列表（可输入商品名（模糊），商品状态，店铺Id，商品类别）
	 * 
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	/**
	 * 添加商品信息以及图片处理
	 * 
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	// 代码重构
	/*
	 * ProductExecution addProduct(Product product, InputStream thumbnail,
	 * String thumbnailName, List<InputStream> productImgList, List<String>
	 * productImgNameList) throws ProductCategoryOperationException;
	 */
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductCategoryOperationException;

	/**
	 * 更新商品
	 * 
	 * @param product
	 * @param thumbnail
	 * @param productImgs
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs)
			throws ProductCategoryOperationException;

	/**
	 * 获取单个商品信息（编辑时）
	 * 
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);

}
