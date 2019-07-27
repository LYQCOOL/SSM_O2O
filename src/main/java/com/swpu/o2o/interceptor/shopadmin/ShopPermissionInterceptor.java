package com.swpu.o2o.interceptor.shopadmin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.swpu.o2o.entity.Shop;

public class ShopPermissionInterceptor extends HandlerInterceptorAdapter{
	/**
	 * 在控制层执行业务前进行用户操作权限拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//从session获取当前选择的店铺
		Shop currentShop = (Shop) request.getSession().getAttribute(
				"currentShop");
		@SuppressWarnings("unchecked")
		//从session中获取当前用户可操作的d店铺列表
		List<Shop> shopList = (List<Shop>) request.getSession().getAttribute(
				"shopList");
		//非空判断
		if (currentShop != null && shopList != null) {
			//遍历可操作的店铺列表
			for (Shop shop : shopList) {
				//如果当前店铺在可操作的列表则返回true，进行接下来操作
				if (shop.getShopId() == currentShop.getShopId()) {
					return true;
				}
			}
		}
		//不满足验证，返回false
		return false;
	}
}
