package com.yowyob.gestion_ressources.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yowyob.gestion_ressources.domain.model.Image;
import com.yowyob.gestion_ressources.domain.model.Ressource;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
    // @Query("select i from Image i where i.sender = ?1")
	List<Image> findByRessource(Ressource r);
}
