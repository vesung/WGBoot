package com.github.waboot.platform.login;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.github.waboot.platform.db.PlatformDao;
import com.github.waboot.platform.license.LicenseEntry;
import com.github.waboot.platform.license.LisenceException;

/**
 * 登录验证方法
 * @author wangjing.dc@qq.com
 *
 */
public class PlatformShiroRealm extends AuthorizingRealm{

	private static final Logger log = Logger.getLogger(PlatformShiroRealm.class);
	
	private PlatformDao platformDao;
	
	// 获取授权信息
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(log.isDebugEnabled()){
			log.debug("WaBootShiroRealm doGetAuthorizationInfo.");
		}
		
		principals.fromRealm(getName()).iterator().next(); 
		  
		return null;
	}

	//获取认证信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		
		validateLisence();
		
		// 将接受的参数强转成可用的UsernamePasswordToken类型
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		// 通过表单接收的用户名
		String userid = token.getUsername();
		Map<String, Object> user = findUserInfo(userid);
		if(user == null) {
			throw new UnknownAccountException();
		}
		
		UserInfo userInfo = wrapUserInfo(user);
		userInfo.setIP(token.getHost());
		
		if("locked".equals(userInfo.getIP())){
			throw new LockedAccountException();
		}
		
		// 得到当前正在执行的用户
		Subject currentUser = SecurityUtils.getSubject();
		// 给当前currentUser会话中设置属性为UserInfo的表单接收对象userinfo
		currentUser.getSession().setAttribute("UserInfo", userInfo);

		return new SimpleAuthenticationInfo(userInfo, 
				MapUtils.getString(user, "PASSWORD", ""), userid);
	}

	/**
	 * 验证lisence
	 */
	private void validateLisence() {
		boolean validated = LicenseEntry.validate();
		if (!validated) {
			throw new LisenceException("License valid fail!");
		}
	}

	/**
	 * 封装UserInfo对象
	 * @param user
	 * @return
	 */
	private UserInfo wrapUserInfo(Map<String, Object> user) {
		if(user == null) return null;
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(MapUtils.getString(user, "ID"));
		userInfo.setUserName(MapUtils.getString(user, "USER_NAME"));
		userInfo.setOrgId(MapUtils.getString(user, "ORG_ID"));
		userInfo.setOrgName((String)user.get("ORG_NAME"));
		
		return userInfo;
	}

	/**
	 * 查询用户信息
	 * @param userid 用户id
	 * @param password 用户密码
	 * @param ip 终端ip
	 * @return
	 */
    private Map<String, Object> findUserInfo(String userid){    	
		List<Map<String, Object>> list = platformDao.queryUserById(userid);
		
		// 用户不存在
		if(list.size() <= 0){
			return null;
		}else {
			return list.get(0);
		}
    }

	public void setPlatformDao(PlatformDao platformDao) {
		this.platformDao = platformDao;
	}
	



}
