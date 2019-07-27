package com.swpu.o2o.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{

	@Autowired
	private ShopCategoryDao shopCategoryDao;
	@Test
	public void testqueryShopCategory(){
		List <ShopCategory> shopCategoryList=shopCategoryDao.queryShopCategory(null);
		assertEquals(2,shopCategoryList.size());
		ShopCategory testShopcategory=new ShopCategory();
		ShopCategory parentShopcategory=new ShopCategory();
		parentShopcategory.setShopCategoryId(1L);
		testShopcategory.setParent(parentShopcategory);
		shopCategoryList=shopCategoryDao.queryShopCategory(testShopcategory);
		assertEquals(1,shopCategoryList.size());
		System.out.println(shopCategoryList.get(0).getShopCategoryName());


		
	}
}
