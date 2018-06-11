package com.github.waboot.platform.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.waboot.platform.common.Paramter;
import com.github.waboot.platform.common.Results;
import com.github.waboot.platform.login.UserInfo;


/**
 * 服务请求入口Controller
 * 
 * @author wangjing.dc@qq.com
 * 
 */
@Controller
@RequestMapping("/service.do")
public class MainController extends AbstractController{

	private static final Logger log = Logger.getLogger(MainController.class);

	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Object doAction(@RequestBody Map<String, Object> param,
			HttpServletRequest request) throws Exception {
		
		log.debug("MainController 接收到的参数:" + param);
		
		String serviceName = MapUtils.getString(param, Paramter.PARAM_SERVICE);
		try{
			UserInfo userInfo = this.findUserInfo(request, param);
			return this.doService(serviceName, param, userInfo, request);
		}catch(Throwable e){
			String errMsg = String.format("执行服务[%s]错误", serviceName);
			log.error(errMsg, e);
			return Results.buildErrorResult("serviceerror", errMsg);
		}
	}
	

}
