package com.ocean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动入口类
 *
 * @since 1.0.0 2020年02月23日
 * @author <a href="https://126.com">Hongyu Jiang</a>
 */
@SpringBootApplication
public class OceanOnlineManageApplication {
	private  static Logger logger = LoggerFactory.getLogger(OceanOnlineManageApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(OceanOnlineManageApplication.class, args);
		logger.info("[OceanOnlineManage服务] 已启动");
	}

}
