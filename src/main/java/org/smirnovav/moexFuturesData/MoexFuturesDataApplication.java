package org.smirnovav.moexFuturesData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MoexFuturesDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoexFuturesDataApplication.class, args);
	}

}
