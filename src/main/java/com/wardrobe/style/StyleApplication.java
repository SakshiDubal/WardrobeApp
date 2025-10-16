package com.wardrobe.style;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.wardrobe.style")
@EnableScheduling
public class StyleApplication {
	public static void main(String[] args) {
		SpringApplication.run(StyleApplication.class, args);
	}
}


