package com.fxg.house.viewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy
@EnableTransactionManagement
@SpringBootApplication
public class HouseViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseViewerApplication.class);
	}
}
