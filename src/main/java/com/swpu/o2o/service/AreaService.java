package com.swpu.o2o.service;

import java.util.List;

import com.swpu.o2o.entity.Area;

public interface AreaService {
	//区域信息key
	public final static String AREALISTKEY="arealist";
	/**
	 * 获取区域信息。首先从缓存中获取
	 * @return
	 */
	public List<Area> getAreaList();

}
