package com.swpu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.entity.Product;
import com.swpu.o2o.entity.Shop;

public class ProductDaoTest extends BaseTest{
	@Autowired
	private ProductDao productDao;
	
	@Test
	@Ignore
	public void testAinsertProduct(){
		Product product=new Product();
		product.setCreateTime(new Date());
		product.setEnableStatus(1);
		product.setImgAddr("test");
		product.setNormalPrice("120");
		product.setPriority(30);
		product.setProductDesc("哈哈");
		product.setProductName("hello");
		product.setPromotionPrice("34");
		Shop shop=new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		int effectedNUm=productDao.insertProduct(product);
		assertEquals(1,effectedNUm);
	
		
	}
	@Test
	@Ignore
	public void TestBupdateProduct(){
		Product product=new Product();
		product.setCreateTime(new Date());
		product.setEnableStatus(1);
		product.setImgAddr("哦哈哟\"");
		product.setNormalPrice("120");
		product.setPriority(30);
		product.setProductDesc("哈勒");
		product.setProductName("hello");
		product.setPromotionPrice("340");
		product.setProductId(1L);
		Shop shop=new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		int effectedNum=productDao.updateProduct(product);
		assertEquals(1,effectedNum);
	}
	@Test
	@Ignore
	public void TestCqueryProductCount(){
		Product productCondition=new Product();
		Shop shop=new Shop();
		shop.setShopId(1L);
		productCondition.setShop(shop);
		productCondition.setEnableStatus(1);
		int effectedsNum=productDao.queryProductCount(productCondition);
		assertEquals(2, effectedsNum);
	}
	@Test
	@Ignore
	public void TestDdeleteProduc(){
		int effectedNum=productDao.deleteProduct(1L, 1L);
		assertEquals(1, effectedNum);
	}
	@Test
	@Ignore
	public void TestEqueryProductById(){
		Product product = productDao.queryProductByProductId(3L);
		System.out.println(product.getProductDesc());
	}
	@Test
	@Ignore
	public void TestEqueryProductList(){
		List<Product> productList=new ArrayList<Product>();
		Product productCondition=new Product();
		Shop shop=new Shop();
		shop.setShopId(1L);
		productCondition.setShop(shop);
		productList=productDao.queryProductList(productCondition, 0, 3);
		System.out.println(productList.size());
	}
	@Test
	public void TestUpdateProductCategoryToNull(){
		long productCategoryId=1L;
		int effectedNum=productDao.updateProductCategoryToNull(productCategoryId);
		assertEquals(2,effectedNum);
	}
}
