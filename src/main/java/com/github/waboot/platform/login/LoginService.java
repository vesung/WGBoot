package com.github.waboot.platform.login;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.github.waboot.platform.common.Paramter;
import com.github.waboot.platform.common.Result;
import com.github.waboot.platform.common.Results;
import com.github.waboot.platform.license.LisenceException;
import com.github.waboot.platform.services.IService;

/**
 * 系统登录服务
 * @author wangjing.dc@qq.com
 *
 */
public class LoginService implements IService{
	private static final Logger log = Logger.getLogger(LoginService.class);

	@Override
	public Result execute(Paramter params) {
		
		String user_id =  params.getString("userid");
		String password = params.getString("password");
		String clientId = params.getString("clientid");
		
		try {
			//使用apache shiro控件，使用SecurityUtils.getSubject()得到当前正在执行的主题
 			Subject currentUser = SecurityUtils.getSubject();
 			UsernamePasswordToken token = new UsernamePasswordToken(user_id,
					password);
 			token.setRememberMe(false);
            token.setHost(clientId);
            currentUser.login(token);
            
			//得到当前的会话信息，设置超时时间单位毫秒，时间为28800000
			SecurityUtils.getSubject().getSession().setTimeout(28800000); 
			
			return Results.buildSuccessResult("platform/main.html");
		} catch ( UnknownAccountException uae ) {
			return Results.buildErrorResult("用户不存在");
		} catch ( IncorrectCredentialsException ice ) {
			return Results.buildErrorResult("密码错误");
		} catch ( LockedAccountException lae ) {
			return Results.buildErrorResult("用户已冻结");
		} catch (AuthenticationException authe) {
			return Results.buildErrorResult(authe.getClass().getTypeName(),
					authe.getMessage());
		} catch (LisenceException le) {
			return Results.buildErrorResult(le.getMessage());
		} catch(Throwable e) {
			log.error("系统登录错误" , e);
			return Results.buildErrorResult(e.getMessage());
		}
		
	}
	
	
}
