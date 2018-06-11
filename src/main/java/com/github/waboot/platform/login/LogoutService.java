/**
 * Ebring 2003 copyright
 * 
 */
package com.github.waboot.platform.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.github.waboot.platform.common.Paramter;
import com.github.waboot.platform.common.Result;
import com.github.waboot.platform.common.Results;
import com.github.waboot.platform.services.IService;

/**
 *
 * 描述: 退出登录服务
 * @author wangjing.dc@qq.com
 */
public class LogoutService  implements IService{
	
	@Override
	public Result execute(Paramter params) {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		
		return Results.buildSuccessResult();
	}



}
