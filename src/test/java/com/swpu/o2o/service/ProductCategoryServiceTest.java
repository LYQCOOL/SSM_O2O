package com.swpu.o2o.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;

public class ProductCategoryServiceTest extends BaseTest{
	@Autowired
	private ProductCategoryService productCategoryService;
	@Test
	public void testGetproductCategoryList(){
		long shopId=1L;
		assertEquals(1,productCategoryService.getproductCategoryList(shopId).size());
	}
	

}
