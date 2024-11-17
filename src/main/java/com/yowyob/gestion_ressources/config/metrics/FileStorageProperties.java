package com.yowyob.gestion_ressources.config.metrics;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "data")
public class FileStorageProperties {
	private String productDir;
}