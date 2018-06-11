package com.github.waboot.platform.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

/**
 * 用于封装请求参数
 * @author wangjing.dc@qq.com
 *
 */
public class Paramter extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	
	/**系统时间*/
	public static final String PARAM_LOGTIME = "sys_LOGTIME";
	/**当前用户id*/
	public static final String PARAM_USER_ID = "sys_USER_ID";
	/**用户名称*/
	public static final String PARAM_USER_NAME = "sys_USER_NAME";
	/**系统路径*/
	public static final String PARAM_REALPATH = "sys_REALPATH";
	/**机构id*/
	public static final String PARAM_ORG_ID = "sys_ORG_ID";
	/**机构名称*/
	public static final String PARAM_ORG_NAME = "sys_ORG_NAME";
	/**客户端ip*/
	public static final String PARAM_CLIENT_IP = "sys_CLIENT_IP";
	/**服务标识*/
	public static final String PARAM_SERVICE = "SERVICE";

	/**
	 * 安全提取value， 若不能存在返回null
	 * @param paramId 参数id
	 * @return
	 */
	public String getString(String paramId) {
		return MapUtils.getString(this, paramId);
	}

	public static Paramter parse(Map<String, Object> param) {
		Paramter newParam = new Paramter();
		newParam.putAll(param);
		
		return newParam;
	}

}
