package com.yowyob.gestion_ressources.api.docs;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

  @Value("${yowyob.openapi.dev-url}")
  private String devUrl;

  @Value("${yowyob.openapi.prod-url}")
  private String prodUrl;

  @Value("${yowyob.openapi.url}")
  private String UrlAdress;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Url du serveur en environnement de développement");

    Server prodServer = new Server();
    prodServer.setUrl(prodUrl);
    prodServer.setDescription("Url du serveur en environnement de production");

    Contact contact = new Contact();
    contact.setEmail("f");
    contact.setName("YowYob");
    contact.setUrl(UrlAdress);

    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("API de gestion des ressources")
        .version("1.0")
        .contact(contact)
        .description("Cet API est un endpoint pour la gestion des ressources.").termsOfService("http://localhost:4000/ressources")
        .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
  }
}