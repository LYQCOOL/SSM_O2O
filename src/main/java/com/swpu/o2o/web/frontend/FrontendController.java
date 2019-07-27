package com.swpu.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/frontend", method = { RequestMethod.GET })
public class FrontendController {
	@RequestMapping(value="/index")
	private String shopOperation(){
		return "frontend/index";
	}
	
	@RequestMapping(value="/shoplist")
	private String shopList(){
		return "frontend/shoplist";
	}
	/**
	 * 店铺详情页路由
	 * @return
	 */
	@RequestMapping(value="/shopdetail")
	private String shopDetail(){
		return "frontend/shopdetail";
	}
	/**
	 * 商品详情路由
	 */
	@RequestMapping(value="/productdetail")
	private String productdetail(){
		return "frontend/productdetail";
	}
	

}
