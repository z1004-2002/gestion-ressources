package com.yowyob.gestion_ressources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableCassandraRepositories(basePackages = "com.yowyob.gestion_ressources.infrastructure.persistence.repository")
public class GestionRessourcesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionRessourcesApplication.class, args);
	}

}
