package com.github.waboot.platform.common;

/**
 * 结果集封装类
 * @author wangjing.dc@qq.com
 *
 */
public class Result {

	// 成功标志
	private boolean success;
	// 结果数据
	private Object data;
	// 状态码
	private String code;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


}
