package com.yowyob.gestion_ressources.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.yowyob.gestion_ressources.domain.model.Etat;
import com.yowyob.gestion_ressources.domain.model.Ressource;

@Repository
public interface RessourceRepository extends CassandraRepository<Ressource, String> {
    @Query("SELECT * FROM ressources WHERE state = ?0 ALLOW FILTERING")
    List<Ressource> findByState(Etat state);
    @Query("SELECT * FROM ressources WHERE domainKey = ?0 ALLOW FILTERING")
    List<Ressource> findByDomainKey(String domainKey);
    @Query("SELECT * FROM ressources WHERE domainKey = ?0 and state = ?1 ALLOW FILTERING")
    List<Ressource> findByDomainKeyState(String domainKey, Etat state);

    @Query("SELECT * FROM ressources WHERE idOwner = ?0 ALLOW FILTERING")
    List<Ressource> findByOwnerId(String idOwner);
}
