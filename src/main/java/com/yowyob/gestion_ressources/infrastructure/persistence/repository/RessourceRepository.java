package com.yowyob.gestion_ressources.infrastructure.persistence.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.yowyob.gestion_ressources.domain.model.Ressource;

public interface RessourceRepository extends CassandraRepository<Ressource,String>{
    
}
