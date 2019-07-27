package com.swpu.o2o.dto;

import java.util.List;

import com.swpu.o2o.entity.LocalAuth;
import com.swpu.o2o.enums.LocalAuthStateEnum;

public class LocalAuthExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//用户信息
	private LocalAuth localAuth;
	//用户信息列表
	private List<LocalAuth> localAuthList; 
	//用户数量
	private int count;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public LocalAuth getLocalAuth() {
		return localAuth;
	}
	public void setLocalAuth(LocalAuth localAuth) {
		this.localAuth = localAuth;
	}
	public List<LocalAuth> getLocalAuthList() {
		return localAuthList;
	}
	public void setLocalAuthList(List<LocalAuth> localAuthList) {
		this.localAuthList = localAuthList;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public LocalAuthExecution(){
		
	}
	//失败时的构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//成功时构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum,LocalAuth localAuth){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.localAuth=localAuth;
	}
	//成功获取用户信息列表时构造器
	public LocalAuthExecution(LocalAuthStateEnum stateEnum,
			List<LocalAuth> localAuthList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.localAuthList = localAuthList;
	}
}
