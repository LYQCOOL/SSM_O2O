package com.swpu.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.dto.ImageHolder;
import com.swpu.o2o.dto.ProductExecution;
import com.swpu.o2o.entity.Product;
import com.swpu.o2o.entity.ProductCategory;
import com.swpu.o2o.entity.Shop;
import com.swpu.o2o.enums.ProductStateEnum;
import com.swpu.o2o.exceptions.ProductOperationException;

public class ProductServiceTest extends BaseTest {
	@Autowired
	private ProductService productService;

	@Test
	@Ignore
	public void testAAddProduct() {
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		ProductCategory pc = new ProductCategory();
		pc.setProductCategoryId(1L);
		product.setShop(shop);
		product.setProductCategory(pc);
		product.setProductName("测试商品一");
		product.setProductDesc("测试商品一");
		product.setPriority(30);
		product.setCreateTime(new Date());
		// 创建缩略图文件流
		File thumnailFile = new File("G:\\images\\item\\shop\\16\\20170606090259397060.png");
		File productImg1=new File("G:\\images\\item\\shop\\16\\2017060608534289617.png");
		File productImg2=new File("G:\\images\\item\\shop\\16\\2017060608574074561.jpg");

		List<ImageHolder> imageHolderList=new ArrayList<ImageHolder>();
		try {
			InputStream is = new FileInputStream(thumnailFile);
			InputStream is1=new FileInputStream(productImg1);
			InputStream is2=new FileInputStream(productImg2);
			ImageHolder imageHolder = new ImageHolder(thumnailFile.getName(), is);
			imageHolderList.add(new ImageHolder(productImg1.getName(), is1));
			imageHolderList.add(new ImageHolder(productImg2.getName(), is2));
			//添加商品并验证
			ProductExecution pe=productService.addProduct(product, imageHolder, imageHolderList);
			assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	@Ignore
	public void testModifyProduct() throws ProductOperationException,FileNotFoundException{
		Product product=new Product();
		Shop shop=new Shop();
		ProductCategory pc=new ProductCategory();
		pc.setProductCategoryId(1L);
		shop.setShopId(1L);
		product.setShop(shop);
		product.setProductId(2L);
		product.setProductName("正式的商品2");
		product.setProductDesc("正式的商品");
		product.setProductCategory(pc);
		//创建图片流
		File thumbnailFile=new File("G:\\images\\item\\shop\\21\\2017060609194286080.jpg");
		InputStream is=new FileInputStream(thumbnailFile);
		//创建缩略图
		ImageHolder thumbnail=new ImageHolder(thumbnailFile.getName(),is);
		//创建详情图
		File productImg1=new File("G:\\images\\item\\shop\\27\\2017060715512185473.jpg");
		InputStream is1=new FileInputStream(productImg1);
		ImageHolder productHolder1=new ImageHolder(productImg1.getName(),is1);
		File productImg2=new File("G:\\images\\item\\shop\\26\\2017060609431259039.png");
		InputStream is2=new FileInputStream(productImg2);
		ImageHolder productHolder2=new ImageHolder(productImg2.getName(),is2);
		List<ImageHolder> productImageHolder=new ArrayList<ImageHolder>();
		productImageHolder.add(productHolder1);
		productImageHolder.add(productHolder2);
		//更新并验证
		ProductExecution pe=productService.modifyProduct(product, thumbnail,productImageHolder);
		assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());



	}
	@Test
	public void testqueryProductlist(){
		Product productCondition=new Product();
		Shop shop=new Shop();
		shop.setShopId(1L);
		productCondition.setShop(shop);
		ProductExecution pe=productService.getProductList(productCondition, 0, 3);
		System.out.println("获取的商品数量："+pe.getProductList().size());
	}
}
