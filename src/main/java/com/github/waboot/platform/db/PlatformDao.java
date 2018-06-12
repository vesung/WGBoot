package com.github.waboot.platform.db;

import java.util.List;
import java.util.Map;

/**
 * @author wangjing.dc@qq.com
 *
 */
public interface PlatformDao {

	public List<Map<String, Object>> queryUserById(String userid);

}
