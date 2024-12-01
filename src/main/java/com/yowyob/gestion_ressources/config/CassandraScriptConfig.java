package com.yowyob.gestion_ressources.config;

import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.cassandra.core.cql.CqlTemplate;

@Configuration
public class CassandraScriptConfig {

    private static final Logger logger = LoggerFactory.getLogger(CassandraScriptConfig.class);
    private final CqlTemplate cqlTemplate;

    @Value("${cassandra.schema.script}")
    private String schemaScriptPath;

    public CassandraScriptConfig(CqlTemplate cqlTemplate) {
        this.cqlTemplate = cqlTemplate;
    }

    @Bean
    public CommandLineRunner loadCqlScript() {
        return args -> {
            try {
                logger.info("Chargement du script CQL depuis : {}", schemaScriptPath);

                // Charger le fichier .cql
                Path cqlFilePath = new ClassPathResource(schemaScriptPath).getFile().toPath();
                String cqlScript = Files.readString(cqlFilePath);

                // Exécuter chaque commande CQL
                for (String cqlCommand : cqlScript.split(";")) {
                    if (!cqlCommand.trim().isEmpty()) {
                        logger.info("Exécution de la commande CQL : {}", cqlCommand.trim());
                        cqlTemplate.execute(cqlCommand.trim());
                    }
                }
            } catch (Exception e) {
                logger.error("Erreur lors du chargement du script CQL", e);
                throw e;
            }
        };
    }
}
