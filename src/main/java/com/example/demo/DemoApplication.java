package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@EnableAspectJAutoProxy
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	/**
//	 * 載入預設資料
//	 * @param etlService
//	 * @return
//	 */
//	@Bean
//	ApplicationListener<ApplicationStartedEvent> testReadData(
//			ETLChatModelService etlService) {
//		return event -> {
//			log.info("Started...");
//			etlService.loadDataAsDocument();
//			log.info("Load Data End...");
//		};
//	}


}
