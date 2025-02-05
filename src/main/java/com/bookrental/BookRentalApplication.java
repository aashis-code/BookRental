package com.bookrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan
@EnableJpaRepositories
@EnableScheduling
public class BookRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookRentalApplication.class, args);
	}

}
