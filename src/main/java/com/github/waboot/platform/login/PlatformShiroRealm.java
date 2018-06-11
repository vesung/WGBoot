package com.github.waboot.platform.login;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.github.waboot.platform.license.LicenseEntry;
import com.github.waboot.platform.db.PlatformDao;

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
			AuthenticationToken authcToken ) throws AuthenticationException {
		//参数authcToken中存储着输入的用户和密码
		if(log.isDebugEnabled()){
			log.debug("MyShiroRealm doGetAuthenticationInfo.");
		}
		
		if(!LicenseEntry.validate()){
			log.error("License valid fail!");
			return null;
		}
		//将接受的参数强转成可用的UsernamePasswordToken类型
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken; 
		System.out.println("host:"+token.getHost());
		
	      // 通过表单接收的用户名
	      String userid = token.getUsername(); 
	      //将token.getPassword()转换成String类型
	      String password=String.valueOf(token.getPassword());
	      String ip=token.getHost();
	      
	      UserInfo userInfo= getUserInfo(userid,password,ip);
	      //得到当前正在执行的用户
		  Subject currentUser = SecurityUtils.getSubject();
		  //给当前currentUser会话中设置属性为UserInfo的表单接收对象userinfo
		  currentUser.getSession().setAttribute("UserInfo", userInfo);
		  log.debug("userInfo=" + userInfo);
		  
		  if(userInfo!=null){
			  return new SimpleAuthenticationInfo(userInfo,  
					  userInfo.getPassword(),userid);
		  }
		  
	
		return null;
	}

	/**
	 * 查询用户信息
	 * @param userid 用户id
	 * @param password 用户密码
	 * @param ip 终端ip
	 * @return
	 */
    private UserInfo getUserInfo(String userid,String password,String ip){
    	UserInfo userInfo=null;
    	
		List<Map<String, Object>> list = platformDao.queryUserById(userid);
		
		// 用户不存在
		if(list.size() <= 0){
			throw new UnknownAccountException("用户不存在");
		}else {
			Map<String, Object> map=list.get(0);
			
			String pwd = MapUtils.getString(map, "PASSWORD");
			if(!(password.equals(pwd))) {
				throw new ConcurrentAccessException("密码错误");
			}
			
			String status = MapUtils.getString(map, "STATUS");
			if("locked".equals(status)){
				throw new LockedAccountException("用户冻结");
			}
			
			userInfo=new UserInfo();
			userInfo.setUserId(userid);
			userInfo.setUserName(MapUtils.getString(map, "USER_NAME"));
			userInfo.setOrgId(MapUtils.getString(map, "ORG_ID"));
			userInfo.setOrgName((String)map.get("ORG_NAME"));
		}
		
    	return userInfo;
    }

	public void setPlatformDao(PlatformDao platformDao) {
		this.platformDao = platformDao;
	}
	
}
