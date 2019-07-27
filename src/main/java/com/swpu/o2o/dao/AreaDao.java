package com.swpu.o2o.dao;

import java.util.List;

import com.swpu.o2o.entity.Area;

public interface AreaDao {
	/*
	 * 列出区域列表
	 * @return arealist
	 */
	List <Area> queryArea();
}
