package com.yowyob.gestion_ressources.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;

@Configuration
// @EnableCassandraRepositories(basePackages = "com.yowyob.gestion_ressources.infrastructure.persistence.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "gestion_ressources";
    }


    @Override
    public String getContactPoints() {
        return "127.0.0.1";
    }

    @Override
    protected int getPort() {
        return 9042;
    }

    @Override
    public String getLocalDataCenter() {
        return "datacenter1";
    }

    @Override
    public SchemaAction getSchemaAction(){
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
}