package com.swpu.o2o.entity;

import java.util.Date;

public class Area {
	/*
	 * 地区表
	 */
	// ID
	private Integer areaID;
	// 名称
	private String areaName;
	// 权重
	private Integer priority;
	// 创建时间
	private Date createdateTime;
	// 更新时间
	private Date lastEditTime;

	

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}


	public Integer getAreaID() {
		return areaID;
	}

	public void setAreaID(Integer areaID) {
		this.areaID = areaID;
	}

	public Date getCreatedateTime() {
		return createdateTime;
	}

	public void setCreatedateTime(Date createdateTime) {
		this.createdateTime = createdateTime;
	}

	public Date getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
}
