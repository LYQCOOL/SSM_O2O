package com.swpu.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.dto.ImageHolder;
import com.swpu.o2o.dto.ShopExecution;
import com.swpu.o2o.entity.Area;
import com.swpu.o2o.entity.PersonInfo;
import com.swpu.o2o.entity.Shop;
import com.swpu.o2o.entity.ShopCategory;
import com.swpu.o2o.enums.ShopStateEnum;
import com.swpu.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testQueryShopList(){
		Shop shopCondition=new Shop();
		ShopCategory sc=new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		shopCondition.setShopName("七");
		ShopExecution se=shopService.getShopList(shopCondition, 1, 2);
		System.out.println("店铺列表数："+se.getShoplist().size());
		System.out.println("店铺总数："+se.getCount());

		
	}
	
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改了的店名");
		File shopImg=new File("G:\\images\\item\\shop\\15\\2017060523302118864.jpg");
		InputStream is=new FileInputStream(shopImg);
		ImageHolder imageHolder=new ImageHolder("da.jpg",is);
		ShopExecution shopExecution=shopService.modifyShop(shop, imageHolder);
		System.out.println("新的图片地址："+shopExecution.getShop().getShopImg());
	}
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaID(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺again2");
		shop.setShopDesc("店铺描述2");
		shop.setShopAddr("测试地2址");
		shop.setShopImg("图片2");
		shop.setPhone("test2");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		shop.setPriority(1);
		File shopImg = new File("D:\\default.jpg");
		InputStream is=new FileInputStream(shopImg);
		ImageHolder imageHolder=new ImageHolder(shopImg.getName(), is);
		ShopExecution shopExcution = shopService.addShop(shop, imageHolder);
		assertEquals(ShopStateEnum.NULL_SHOP.getState(), shopExcution.getState());

	}

}
