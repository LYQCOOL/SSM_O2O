package com.swpu.o2o.dto;

import java.util.List;

import com.swpu.o2o.entity.WechatAuth;
import com.swpu.o2o.enums.WechatAuthStateEnum;

public class WechatExecution {
	// 结果状态
		private int state;
		// 状态标识
		private String stateInfo;
		// 微信数量
		private int count;
		// 操作的微信（增删改查的时候用到）
		private WechatAuth wechatAuth;
		// 微信列表
		private List<WechatAuth> wechatAuthList;

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

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		
		// 店铺操作失败时使用的构造器
		public WechatAuth getWechatAuth() {
			return wechatAuth;
		}

		public void setWechatAuth(WechatAuth wechatAuth) {
			this.wechatAuth = wechatAuth;
		}

		public List<WechatAuth> getWechatAuthList() {
			return wechatAuthList;
		}

		public void setWechatAuthList(List<WechatAuth> wechatAuthList) {
			this.wechatAuthList = wechatAuthList;
		}

		public WechatExecution(WechatAuthStateEnum stateEnum) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();

		}

		// 店铺操作成功时构造器
		public WechatExecution(WechatAuthStateEnum stateEnum, WechatAuth WechatAuth) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.wechatAuth = WechatAuth;

		}

		// 店铺操作成功时构造器
		public WechatExecution(WechatAuthStateEnum stateEnum, List<WechatAuth> wechatAuthList) {
			this.state = stateEnum.getState();
			this.stateInfo = stateEnum.getStateInfo();
			this.wechatAuthList = wechatAuthList;

		}

}
