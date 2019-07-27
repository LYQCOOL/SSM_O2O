package com.swpu.o2o.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/local")
public class LocalController {
	/**
	 * 绑定账号路由
	 */
	@RequestMapping(value="/accountbind",method=RequestMethod.GET)
	private String accountbind(){
		return "local/accountbind";
	}
	/**
	 * 修改密码路由
	 * @return
	 */
	@RequestMapping(value="/changepsw")
	private String changepsw(){
		return "local/changepsw";
	}
	/**
	 * 登录路由
	 * @return
	 */
	@RequestMapping(value="/login")
	private String login(){
		return "local/login";
	}
	
}
