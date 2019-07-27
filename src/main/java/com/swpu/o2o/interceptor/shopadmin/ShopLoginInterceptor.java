package com.swpu.o2o.interceptor.shopadmin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.swpu.o2o.entity.PersonInfo;
/**
 * 管理系统拦截器
 * @author ASUS
 *
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//从session中取出用户信息
		Object userObj=request.getSession().getAttribute("user");
		if(userObj!=null){
			//若用户信息不为空则将session里的用户信息转换成PersonInfos实体类对象
			PersonInfo user=(PersonInfo)userObj;
			if(user!=null&&user.getUserId()!=null&&user.getUserId()>0&&user.getEnableStatus()==1){
				//若通过验证则返回true，拦截器返回true，控制层业务执行
				return true;
			}
			
		}
		//若不满验证，则直接跳转到登录界面
		PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open ('" + request.getContextPath()
		+ "/local/login?usertype=2','_self')");
		out.println("</script>");
		out.println("<html>");
		return false;
	}
	
}
