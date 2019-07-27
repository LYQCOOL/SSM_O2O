package com.swpu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.entity.Area;
import com.swpu.o2o.entity.PersonInfo;
import com.swpu.o2o.entity.Shop;
import com.swpu.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	@Test
	@Ignore
	public void testQueryCount(){
		Shop shopCondition=new Shop();
		ShopCategory childshopCategory=new ShopCategory();
		ShopCategory parentshopCategory=new ShopCategory();
		parentshopCategory.setShopCategoryId(1L);
		childshopCategory.setParent(parentshopCategory);
		shopCondition.setShopCategory(childshopCategory);
		int t=shopDao.queryShopCount(shopCondition);
		System.out.println("总条数为："+t);
		List<Shop> shopList=shopDao.queryShopList(shopCondition,0, 6);
		System.out.println("符合条件的店铺数："+shopList.size());
	}
	@Test
	public void testQueryShopList(){
		Shop shopCondition=new Shop();
		PersonInfo owner=new PersonInfo();
		ShopCategory sc=new ShopCategory();
		shopCondition.setShopName("七");
		sc.setShopCategoryId(8L);
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		shopCondition.setShopCategory(sc);
		List<Shop> list=shopDao.queryShopList(shopCondition, 0, 5);
		System.out.println("店铺条数："+list.size());
		
	}
	@Test
	@Ignore
	public void testqueryByShopId(){
		long shopId=1;
		Shop shop=shopDao.queryByshopId(shopId);
		System.out.println("AreaID:"+shop.getArea().getAreaID());
		System.out.println("AreaName:"+shop.getArea().getAreaName());

	}

	@Test
	@Ignore
	public void testInsertShop() {
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
		shop.setShopName("测试的店铺");
		shop.setShopDesc("店铺描述");
		shop.setShopAddr("测试地址");
		shop.setShopImg("图片");
		shop.setPhone("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		shop.setPriority(1);
		shop.setLastEditTime(new Date());
		int effectedNum=shopDao.insertShop(shop);
		assertEquals(1,effectedNum); 
		
		
	}
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("测试的店铺哈哈");
		shop.setShopAddr("测试地址haha");
		shop.setLastEditTime(new Date());
		int effectedNum=shopDao.updateShop(shop);
		assertEquals(1,effectedNum); 
		
		
	}
}
