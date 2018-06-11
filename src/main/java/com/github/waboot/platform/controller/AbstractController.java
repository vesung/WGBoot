/**
 * Ebring 2003 copyright
 * 
 */
package com.github.waboot.platform.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.github.waboot.platform.common.Paramter;
import com.github.waboot.platform.common.Result;
import com.github.waboot.platform.common.Results;
import com.github.waboot.platform.login.UserInfo;
import com.github.waboot.platform.services.IService;
import com.google.common.base.Strings;


/**
 *
 * 描述: 
 * @author wangjing.dc@qq.com
 */
public abstract class AbstractController {

	@Autowired
	private ApplicationContext context;
	
	/**
	 * 执行请求的服务
	 * @param param
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Result doService(String serviceName, Map<String, Object> param,
			UserInfo userInfo, HttpServletRequest request) throws Exception {
		
		if (Strings.isNullOrEmpty(serviceName)) {
			return Results.buildErrorResult("noservice", "服务id不能为空");
		}

		if ("loginService".equals(serviceName)) {
			// no-op
		} else if (!SecurityUtils.getSubject().isAuthenticated()) {
			return Results.buildErrorResult("timeout", "登录超时");
		}
		
		if(context.containsBean(serviceName)) {
			return Results.buildErrorResult(String.format("[%s]服务未定义", serviceName));
		}
		if(!(context.getBean(serviceName) instanceof IService)) {
			return Results.buildErrorResult(String.format("[%s]服务未实现Iservice接口", serviceName));
		}

		IService service = (IService) context.getBean(serviceName);
		Paramter patams = Paramter.parse(param);
		this.addCommonParameter(patams, request, userInfo);
		
		return service.execute(patams);
	}

	/**
	 * 添加公共参数
	 * @param param
	 * @param request
	 */
	protected void addCommonParameter(Paramter param, HttpServletRequest request, UserInfo userInfo) {
		param.put(Paramter.PARAM_LOGTIME, getCurrentTime());
		param.put(Paramter.PARAM_CLIENT_IP, this.getClientIP(request));
		param.put(Paramter.PARAM_REALPATH, request.getSession().getServletContext().getRealPath(""));
		
		// 暂放临时数据
		if (userInfo != null) {
			param.put(Paramter.PARAM_USER_ID, userInfo.getUserId());
			param.put(Paramter.PARAM_USER_NAME, userInfo.getUserName());
			param.put(Paramter.PARAM_ORG_ID, userInfo.getOrgId());
			param.put(Paramter.PARAM_ORG_NAME, userInfo.getOrgName());
		}
	}
	
	/**
	 * 查找当前用户信息
	 * @param request
	 * @param param 
	 * @return
	 */
	protected UserInfo findUserInfo(HttpServletRequest request, Map<String, Object> param) {
		Subject currentUser = SecurityUtils.getSubject();

		UserInfo userInfo = (UserInfo) currentUser.getSession().getAttribute(
				"UserInfo");
		
		return userInfo;
	};
	
	
	/**
	 * 获取当前时间。
	 * @return
	 */
	protected String getCurrentTime() {
		java.text.DateFormat format = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Date());
		return time;
	}
	
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	protected String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}


}
