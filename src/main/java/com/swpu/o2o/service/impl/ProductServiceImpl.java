package com.swpu.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpu.o2o.dao.ProductDao;
import com.swpu.o2o.dao.ProductImgDao;
import com.swpu.o2o.dto.ImageHolder;
import com.swpu.o2o.dto.ProductExecution;
import com.swpu.o2o.entity.Product;
import com.swpu.o2o.entity.ProductImg;
import com.swpu.o2o.enums.ProductStateEnum;
import com.swpu.o2o.exceptions.ProductCategoryOperationException;
import com.swpu.o2o.exceptions.ProductOperationException;
import com.swpu.o2o.service.ProductService;
import com.swpu.o2o.util.ImageUtil;
import com.swpu.o2o.util.PageCalculator;
import com.swpu.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductImgDao productImgDao;

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		// 页码转换为数据库中的行码，并调用dao层取指定页码的数据
		int rowIndex=PageCalculator.calculatorRowIndex(pageIndex, pageSize);
		List<Product> productList=productDao.queryProductList(productCondition, rowIndex, pageSize);
		//基于同样的查询条件返回查询的商品的数量
		int count=productDao.queryProductCount(productCondition);
		ProductExecution pe=new ProductExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

	@Override
	public Product getProductById(long productId) {
		// TODO Auto-generated method stub
		return productDao.queryProductByProductId(productId);
	}

	@Override
	// 事务，如果有一个操作不成功回滚
	@Transactional
	// 1.处理缩略图
	// 2.往tb_product写入商品信息，获取productId
	// 3.结合productId批量处理商品详情图
	// 4.将商品详情图列表批量插入tb_product_img
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductCategoryOperationException {
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			// 默认为上架的状态
			product.setEnableStatus(1);
			// 若商品缩略图不为空则添加
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				// 创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品失败");

				}
			} catch (ProductOperationException e) {
				throw new ProductOperationException("创建商品失败：" + e.toString());
			}
			// 若商品详情图不为空
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				addProductImgList(product, productImgHolderList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			// 传参为空则返回空值错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 批量添加图片
	 * 
	 * @param product
	 * @param productImgHolderList
	 */
	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
		// 获取图片存储路径，这里直接存放到相应店铺的文件夹底下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		// 遍历图片一次去处理，并添加进productImg实体类里
		for (ImageHolder productimgHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productimgHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		// 如果确实有图片需要添加，就执行批量添加操作
		if (productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new ProductOperationException("创建商品详情图片失败");

				}

			} catch (ProductOperationException e) {
				throw new ProductOperationException("创建商品详情图片失败:" + e.toString());
			}
		}
	}

	/**
	 * 添加缩略图
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumnailAddr);

	}

	@Override
	@Transactional
	// 1.若缩略图参数有值，则处理缩略图
	// 2.若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product
	// 3.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
	// 4.更新tb_product信息
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs)
			throws ProductCategoryOperationException {
		// TODO Auto-generated method stub
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 给商品设置默认属性
			product.setLastEditTime(new Date());
			// 若商品缩略图不为空且原有缩略图不为空则删除原有缩略图添加
			if (thumbnail != null) {
				// 先获取一遍原有信息，删除删除原有图片地址
				Product tempProduct = productDao.queryProductByProductId(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			// 如果有新存入的商品详情图，则将原先的删除，并添加新的图片
			if (productImgs != null && productImgs.size() > 0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgs);
			}
			try {
				//更新商品信息
				int effectedNum=productDao.updateProduct(product);
				if(effectedNum<=0){
					throw new ProductOperationException("更新商品失败");

				}
				return new ProductExecution(ProductStateEnum.SUCCESS,product);
			} catch (Exception e) {
				throw new ProductOperationException("更新商品失败："+e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 删除原有商品详情图
	 * 
	 * @param productId
	 */
	private void deleteProductImgList(Long productId) {
		// 更具productId获取原有图片
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		// 删除原有的图片
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		// 删除数据库中的原有图片信息
		productImgDao.deleteProductByProductId(productId);

	}

}
