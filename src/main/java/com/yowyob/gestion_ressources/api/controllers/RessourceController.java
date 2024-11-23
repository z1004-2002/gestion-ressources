package com.yowyob.gestion_ressources.api.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping(path = "/api/v1/ressource")
@Tag(name = "Ressource", description = "Endpoints de gestiond des ressources")
@CrossOrigin("*")
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
    @GetMapping("/get_one/{idRessource}")
    public RessourceResponse getMethodName(@PathVariable(name = "idRessource") String idRessource) {
        return ressourceService.getOneRessourceById(idRessource);
    }

    @Operation(summary = "Mettre à jour une ressource", description = "")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/update/{idRessource}")
    public RessourceResponse updateARessource(@PathVariable(name = "idRessource") String idRessource,
            @RequestBody RessourceRequest request) {
        return ressourceService.updateRessource(idRessource, request);
    }

    @Operation(summary = "Supprime une ressource", description = "")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/delete/{idRessource}")
    public String deleteARessource(@PathVariable(name = "idRessource") String idRessource) {
        return ressourceService.deleteRessource(idRessource);
    }
    @Operation(summary = "Changer de propriétaire", description = "")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{idRessource}/owner/{idOwner}")
    public String transfertOwner(@PathVariable(name = "idRessource") String idRessource, @PathVariable(name = "idOwner") String idOwner) {
        return ressourceService.changeOwner(idRessource,idOwner);
    }
}
