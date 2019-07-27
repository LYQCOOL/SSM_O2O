package com.swpu.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//定义访问的路由（html[spring-web中的路由解析]）
@Controller
@RequestMapping(value = "shopadmin", method = { RequestMethod.GET })
public class ShopAdminController {

	@RequestMapping(value="/shopoperation")
	public String shopOperation(){
		return "shop/shopoperation";
	}
	@RequestMapping(value="/shoplist")
	public String shopList(){
		return "shop/shoplist";
	}
	@RequestMapping(value="/shopmanagement")
	public String shopManagement(){
		return "shop/shopmanage";
	}
	@RequestMapping(value="/productcategorymanage")
	public String productCategoryManage(){
		return "shop/productcategorymanage";
	}
	@RequestMapping(value="/productoperation")
	public String productOperation(){
		//转发至商品添加/编辑页面
		return "shop/productoperation";
	}
	@RequestMapping(value="/productmanage")
	public String productManage(){
		//转发至商品添加/编辑页面
		return "shop/productmanage";
	}
}
