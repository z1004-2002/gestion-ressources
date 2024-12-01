package com.yowyob.gestion_ressources.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;

@Configuration
// @EnableCassandraRepositories(basePackages = "com.yowyob.gestion_ressources.infrastructure.persistence.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {
    @Value("${spring.cassandra.keyspace-name}")
    private String keySpaceName;
    @Value("${spring.cassandra.contact-points}")
    private String contactNamePoint;
    @Value("${spring.cassandra.port}")
    private int port;
    @Value("${spring.cassandra.local-datacenter:}")
    private String localDataCenter;

    @Override
    protected String getKeyspaceName() {
        return this.keySpaceName;
    }

    @Override
    public String getContactPoints() {
        return this.contactNamePoint;
    }

    @Override
    protected int getPort() {
        return this.port;
    }

    @Override
    public String getLocalDataCenter() {
        return this.localDataCenter;
    }

    @Override
    public SchemaAction getSchemaAction(){
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
}