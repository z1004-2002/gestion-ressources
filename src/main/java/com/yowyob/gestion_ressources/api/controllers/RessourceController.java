package com.yowyob.gestion_ressources.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yowyob.gestion_ressources.application.dto.RessourceRequest;
import com.yowyob.gestion_ressources.application.dto.RessourceResponse;
import com.yowyob.gestion_ressources.application.services.RessourceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path = "/api/v1/ressource")
@Tag(name = "Ressource", description = "Endpoints de gestiond des ressources")
public class RessourceController {
    @Autowired
    private RessourceService ressourceService;

    @Operation(summary = "Créer une ressource", description = "")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public RessourceResponse postMethodName(@RequestBody RessourceRequest request) {
        return ressourceService.createRessource(request);
    }

    
    @Operation(summary = "Lister toute les ressources une ressource", description = "")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<RessourceResponse> getAllRessource() {
        return ressourceService.getAllRessources();
    }

    @Operation(summary = "Obtenir une ressource", description = "")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get_one/{id_ressource}")
    public RessourceResponse getMethodName(@PathVariable(name = "id_ressource") UUID id_ressource) {
        return ressourceService.getOneRessourceById(id_ressource);
    }

    @Operation(summary = "Mettre à jour une ressource", description = "")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/update/{id_ressource}")
    public RessourceResponse updateARessource(@PathVariable(name = "id_ressource") UUID id_ressource,
            @RequestBody RessourceRequest request) {
        return ressourceService.updateRessource(id_ressource, request);
    }

    @Operation(summary = "Supprime une ressource", description = "")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/delete/{id_ressource}")
    public String deleteARessource(@PathVariable(name = "id_ressource") UUID id_ressource) {
        return ressourceService.deleteRessource(id_ressource);
    }
}
