package com.yowyob.gestion_ressources.infrastructure.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yowyob.gestion_ressources.domain.model.Ressource;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource,UUID>{
    
}
