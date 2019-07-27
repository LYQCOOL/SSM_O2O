package com.swpu.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpu.o2o.dao.ProductCategoryDao;
import com.swpu.o2o.dao.ProductDao;
import com.swpu.o2o.dto.ProductCategoryExecution;
import com.swpu.o2o.entity.ProductCategory;
import com.swpu.o2o.enums.ProductCategoryStateEnum;
import com.swpu.o2o.exceptions.ProductCategoryOperationException;
import com.swpu.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductDao productDao;
	
	@Override
	public List<ProductCategory> getproductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {

		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectedNum <= 0) {
					throw new ProductCategoryOperationException("店铺类别创建失败");
				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategoryList);
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());

			}

		} else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);

		}
	}

	/**
	 * 将此商品里的类别id置为空，再删除该商品类别(事务)
	 */
	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		// TODO 将此商品里的类别id置为空
		//解决tb_product里的商品与该productcategoryId的关联
		try{
		int effectedNum=productDao.updateProductCategoryToNull(productCategoryId);
		if(effectedNum<0){
			throw new ProductCategoryOperationException("商品类别更新失败");
		}
		}
		catch(Exception e){
			throw new ProductCategoryOperationException("商品类别更新错误："+e.getMessage());
		}
		// 删除相应的商品类别
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if (effectedNum <= 0) {
				throw new ProductCategoryOperationException("店铺类别删除失败");
			} else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error:" + e.getMessage());
		}
	}

}
