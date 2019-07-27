package com.swpu.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.entity.ProductCategory;
/*测试顺序标签控制,测试回环
 * 1.MethodSorters.JVM(表示写的顺序【JVM得到的顺序】)；
 * 2.MethodSorters.NAME_ASCENDING(按照方法的名字)testA...,testB.....
 * 3.MethodSorters.DEFAULT：默认的顺序(不可预期)
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest{
	@Autowired
	private ProductCategoryDao productShopCategoryDao;
	@Test
	@Ignore
	public void testQueryProductCategoryList(){
		List<ProductCategory> list=productShopCategoryDao.queryProductCategoryList(1L);
		for(ProductCategory p:list){
			System.out.println(p.getProductCategoryName());
		}
		
	}
	@Test
	@Ignore
	public void testbatchInsertProductCategory(){
		ProductCategory sc1= new ProductCategory();
		sc1.setPriority(2);
		sc1.setProductCategoryName("批量添加的店铺1");
		sc1.setShopId(1L);
		sc1.setCreateTime(new Date());
		ProductCategory sc2= new ProductCategory();
		sc2.setProductCategoryName("批量添加的店铺2");
		sc2.setShopId(1L);
		sc2.setPriority(10);
		sc2.setCreateTime(new Date());
		List<ProductCategory> list=new ArrayList<ProductCategory>();
		list.add(sc1);
		list.add(sc2);
		int effectedNum=productShopCategoryDao.batchInsertProductCategory(list);
		assertEquals(2,effectedNum);
		
	}
	@Test
	public void testbatchDeleteProductCategory() throws Exception{
		long shopId=1L;
		List<ProductCategory> productCategoryList=productShopCategoryDao.queryProductCategoryList(shopId);
		for(ProductCategory pc:productCategoryList){
			if("批量添加的店铺1".equals(pc.getProductCategoryName())||"批量添加的店铺2".equals(pc.getProductCategoryName())){
				int effectedNum=productShopCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
				assertEquals(1,effectedNum);
			}
		}
		
	}
	

}
