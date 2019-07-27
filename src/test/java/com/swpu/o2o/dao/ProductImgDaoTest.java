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
import com.swpu.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {

	@Autowired
	private ProductImgDao productImgDao;

	@Test
	@Ignore
	public void testAbatchInsertProductImg() {
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		ProductImg p1 = new ProductImg();
		p1.setImgAddr("test addr1");
		p1.setImgDesc("test desc1");
		p1.setPriority(1);
		p1.setProductId(1L);
		p1.setCreateTime(new Date());
		ProductImg p2 = new ProductImg();
		p2.setImgAddr("test addr2");
		p2.setImgDesc("test desc2");
		p2.setPriority(3);
		p2.setProductId(1L);
		p2.setCreateTime(new Date());
		productImgList.add(p1);
		productImgList.add(p2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);

	}

	@Test
	@Ignore
	public void testBqueryProductImgList() {
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList = productImgDao.queryProductImgList(1L);
		assertEquals(3, productImgList.size());
	}
	@Test
	public void testCdeleteProductByProductId() {
		int effectdNum=productImgDao.deleteProductByProductId(1L);
		assertEquals(3, effectdNum);
	}
}
