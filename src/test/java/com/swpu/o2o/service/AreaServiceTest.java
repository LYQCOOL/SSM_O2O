package com.swpu.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.swpu.o2o.BaseTest;
import com.swpu.o2o.entity.Area;

public class AreaServiceTest extends BaseTest{
	@Autowired
	private AreaService areaService;
	@Autowired
	private CacheService cacheService;
	@Test
	public void testGetAreaList(){
		List<Area>arealist=areaService.getAreaList();
		assertEquals("西苑",arealist.get(0).getAreaName());
		cacheService.removeFromCatche(areaService.AREALISTKEY);

	}
}
