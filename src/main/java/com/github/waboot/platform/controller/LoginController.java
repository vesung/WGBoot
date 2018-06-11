package com.github.waboot.platform.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.waboot.platform.common.Results;

/**
 * 
 * 描述:系统登录入口
 * @author wangjing.dc@qq.com
 */
@Controller
@RequestMapping("/login.do")
public class LoginController extends AbstractController{

	private static final Logger log = Logger.getLogger(LoginController.class);

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Object doAction(@RequestBody Map<String, Object> param,
			HttpServletRequest request) throws Exception {
		
		try{
			return this.doService("loginService", param, null, request);
		}catch(Exception e){
			log.error("LoginController系统错误", e);
			return Results.buildErrorResult("LoginController系统错误");
		}
		
	}

}
