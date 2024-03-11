package com.agira.shareDrive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ShareDriveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShareDriveApplication.class, args);
	}
}
