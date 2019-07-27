package com.swpu.o2o.entity;

import java.util.Date;

//顾客已领取奖品映射
public class UserAwardMap {
	//主键Id
	private Long userAwardId;
	//创建时间
	private Date cteateTime;
	//使用状态
	private Integer userdStatus;
	//领取奖品所消耗的积分
	private Integer point;
	//顾客信息实体类
	private PersonInfo user;
	//商品信息实体类
	private Award awaed;
	//店铺信息实体类
	private Shop shop;
	//操作员信息实体类
	private PersonInfo operator;
	public Long getUserAwardId() {
		return userAwardId;
	}
	public void setUserAwardId(Long userAwardId) {
		this.userAwardId = userAwardId;
	}
	public Date getCteateTime() {
		return cteateTime;
	}
	public void setCteateTime(Date cteateTime) {
		this.cteateTime = cteateTime;
	}
	public Integer getUserdStatus() {
		return userdStatus;
	}
	public void setUserdStatus(Integer userdStatus) {
		this.userdStatus = userdStatus;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public PersonInfo getUser() {
		return user;
	}
	public void setUser(PersonInfo user) {
		this.user = user;
	}
	public Award getAwaed() {
		return awaed;
	}
	public void setAwaed(Award awaed) {
		this.awaed = awaed;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public PersonInfo getOperator() {
		return operator;
	}
	public void setOperator(PersonInfo operator) {
		this.operator = operator;
	}

}
