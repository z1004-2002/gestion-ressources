package com.yowyob.gestion_ressources.infrastructure.persistence.repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yowyob.gestion_ressources.domain.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
    List<Image> findByRessourceId(UUID id_ressource);
}
