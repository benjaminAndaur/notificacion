package com.edutech.notificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class NotificacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificacionesApplication.class, args);
	}

}
