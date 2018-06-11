package com.github.waboot.platform.common;

/**
 * 结果集工具方法
 * @author wangjing.dc@qq.com
 *
 */
public class Results {

	/**
	 * 快速构建成功结果
	 * @return
	 */
	public static Result buildSuccessResult() {
		return buildSucessResult(null);
	}
	
	/**
	 * 快速构建成功结果
	 * @param msg 成功message
	 * @return
	 */
	public static Result buildSucessResult(Object msg) {
		Result result = new Result();
		result.setSuccess(true);
		result.setCode("success");
		result.setData(msg == null ? "" : msg);
		return result;
	}
	
	/**
	 * 快速构建错误结果
	 * @param msg
	 * @return
	 */
	public static Result buildErrorResult(Object msg) {
		return buildErrorResult(null, msg);
	}

	/**
	 * 快速构建错误结果
	 * @param message
	 */
	public static Result buildErrorResult(String code, Object msg) {
		Result result = new Result();
		result.setSuccess(false);
		result.setCode(code == null ? "error" : code);
		result.setData(msg == null ? "" : msg);
		return result;
	}

}
