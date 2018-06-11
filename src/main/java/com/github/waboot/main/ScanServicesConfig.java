package com.github.waboot.main;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * 描述: 注册service配置文件
 * 
 * @author wangjing.dc@qq.com
 */
@Configuration
@ImportResource(locations= {
		"classpath:applicationContext-platform-service.xml",
		"classpath:applicationContext-waboot-service.xml"})
public class ScanServicesConfig{


}
