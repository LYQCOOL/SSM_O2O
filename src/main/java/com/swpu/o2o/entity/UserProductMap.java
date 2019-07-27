package com.swpu.o2o.entity;

import java.util.Date;

//用户商品映射
public class UserProductMap {
	//主键Id
	private Integer userProductId;
	//创建时间
	private Date creteTime;
	//消费商品所获得的积分
	private Integer point;
	//商品信息实体类
	private Product product;
	//店铺信息实体类
	private Shop shop;
	//操作员信息实体类
	private PersonInfo operator;
	public Integer getUserProductId() {
		return userProductId;
	}
	public void setUserProductId(Integer userProductId) {
		this.userProductId = userProductId;
	}
	public Date getCreteTime() {
		return creteTime;
	}
	public void setCreteTime(Date creteTime) {
		this.creteTime = creteTime;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
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
