package com.adaur.Crawler;

import com.adaur.Crawler.services.TtpServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class RealEstateCrawlerApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(RealEstateCrawlerApplication.class, args);
	}


}
