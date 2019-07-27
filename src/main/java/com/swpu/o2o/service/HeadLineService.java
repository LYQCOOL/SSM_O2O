package com.swpu.o2o.service;

import java.io.IOException;
import java.util.List;

import com.swpu.o2o.entity.HeadLine;

public interface HeadLineService {
	//头条缓存中对应的键
	public final static String HEADLISTKEY="headlist";
	
	/**
	 * 根据传入的条件返回指定的头条列表
	 * @param headLineCondition
	 * @return
	 * @throws IOException
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

}
