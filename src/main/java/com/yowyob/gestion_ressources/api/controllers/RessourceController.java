package com.yowyob.gestion_ressources.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yowyob.gestion_ressources.application.dto.RessourceRequest;
import com.yowyob.gestion_ressources.application.dto.RessourceResponse;
import com.yowyob.gestion_ressources.application.services.RessourceService;
import com.yowyob.gestion_ressources.domain.model.Etat;

import io.swagger.v3.oas.annotations.Operation;

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
// @Tag(name = "Ressource", description = "Endpoints de gestiond des ressources")
@CrossOrigin("*")
public class RessourceController {
    @Autowired
    private RessourceService ressourceService;

    @Operation(summary = "Créer une ressource", description = "Ce endpoint permet de créer une ressource. Bien vouloir passer la clé dd votre domaine en paramètre", tags = "CRUD")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public RessourceResponse postMethodName(@RequestBody RessourceRequest request, @RequestParam("domainKey") UUID domainKey) {
        return ressourceService.createRessource(request, domainKey);
    }

    @Operation(summary = "Lister toute les ressources une ressource", description = "", tags = "CRUD")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<RessourceResponse> getAllRessource() {
        return ressourceService.getAllRessources();
    }
    @Operation(summary = "Lister toute les ressources une ressource", description = "Bien vouloir passer la clé de votre domaine en paramètre", tags = "CRUD")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all/domain")
    public List<RessourceResponse> getAllRessource(@RequestParam("domainKey") UUID domainKey) {
        return ressourceService.getAllRessources(domainKey);
    }

    @Operation(summary = "Lister toute les ressources un owner", description = "Bien vouloir passer l'id de l'owner", tags = "CRUD")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all/owner/{idOwner}")
    public List<RessourceResponse> getRessourcesByIdOwner(@PathVariable("idOwner") UUID idOwner) {
        return ressourceService.getRessourcesByIdOwner(idOwner);
    }

    @Operation(summary = "Obtenir une ressource", description = "Cette requête va prendre comme variable de chemin l'Id de votre ressource", tags = "CRUD")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get_one/{idRessource}")
    public RessourceResponse getMethodName(@PathVariable(name = "idRessource") UUID idRessource) {
        return ressourceService.getOneRessourceById(idRessource);
    }

    @Operation(summary = "Mettre à jour une ressource", description = "Endpoint permettant de mettre à jour tous les information d'une ressouce", tags = "CRUD")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/update/{idRessource}")
    public RessourceResponse updateARessource(@PathVariable(name = "idRessource") UUID idRessource,
            @RequestBody RessourceRequest request) {
        return ressourceService.updateRessource(idRessource, request);
    }

    @Operation(summary = "Supprime une ressource", description = "Supprimer une ressource à Partir de son Id", tags = "CRUD")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/delete/{idRessource}")
    public String deleteARessource(@PathVariable(name = "idRessource") UUID idRessource) {
        return ressourceService.deleteRessource(idRessource);
    }

    @Operation(summary = "Changer de propriétaire", description = "Changer de propriétaire pour une ressource", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{idRessource}/owner/{idOwner}")
    public RessourceResponse transfertOwner(@PathVariable(name = "idRessource") UUID idRessource,
            @PathVariable(name = "idOwner") UUID idOwner) {
        return ressourceService.changeOwner(idRessource, idOwner);
    }

    @Operation(summary = "Rendre disponible une ressource", description = "Permet d'activer une Ressource", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{idRessource}/available")
    public RessourceResponse enableRessource(@PathVariable(name = "idRessource") UUID idRessource) {
        return ressourceService.enableRessource(idRessource);
    }

    @Operation(summary = "Rendre indisponible une ressource", description = "Permet de désactiver une ressource", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{idRessource}/unavailable")
    public RessourceResponse disableRessource(@PathVariable(name = "idRessource") UUID idRessource) {
        return ressourceService.disableRessource(idRessource);
    }

    // @Operation(summary = "Commander une ressource", description = "", tags = "Actions Suplémentaire")
    // @ResponseStatus(HttpStatus.ACCEPTED)
    // @PutMapping("/{idRessource}/command/{quantity}")
    // public RessourceResponse commandRessource(@PathVariable(name = "idRessource") UUID idRessource,
    //         @PathVariable(name = "quantity") Integer quantity) {
    //     return ressourceService.commandRessource(idRessource, quantity);
    // }

    // @Operation(summary = "Annuler la commande d'une ressource", description = "", tags = "Actions Suplémentaire")
    // @ResponseStatus(HttpStatus.ACCEPTED)
    // @PutMapping("/{idRessource}/cancel_command/{quantity}")
    // public RessourceResponse cancelCommandRessource(@PathVariable(name = "idRessource") UUID idRessource,
    //         @PathVariable(name = "quantity") Integer quantity) {
    //     return ressourceService.cancelCommandRessource(idRessource, quantity);
    // }

    @Operation(summary = "Liste des ressource disponible", description = "Lister toutes les resosurces disponibles", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/available")
    public List<RessourceResponse> availableRessource() {
        return ressourceService.availableRessources(Etat.AVAILABLE);
    }

    @Operation(summary = "Liste des ressource disponible", description = "Lister toutes les ressource Indisponbles", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/unavailable")
    public List<RessourceResponse> unavailableRessource() {
        return ressourceService.availableRessources(Etat.UNAVAILABLE);
    }
    @Operation(summary = "Liste des ressource disponible", description = "Ne pas oublier de passer la clé du domain en paramètre", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/available/domain")
    public List<RessourceResponse> availableRessourceDomain(UUID domainKey) {
        return ressourceService.availableRessources(Etat.AVAILABLE);
    }

    @Operation(summary = "Liste des ressource disponible", description = "Ne pas oublier de passer la clé du domain en paramètre", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/unavailable/domain")
    public List<RessourceResponse> unavailableRessourceDomain(UUID domainKey) {
        return ressourceService.availableRessources(Etat.UNAVAILABLE);
    }
}
