package com.yowyob.gestion_ressources.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yowyob.gestion_ressources.application.dto.RessourceRequest;
import com.yowyob.gestion_ressources.application.dto.RessourceResponse;
import com.yowyob.gestion_ressources.application.services.ReservationService;
import com.yowyob.gestion_ressources.application.services.RessourceService;
import com.yowyob.gestion_ressources.domain.model.Etat;
import com.yowyob.gestion_ressources.domain.model.Reservation;

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
// @Tag(name = "Ressource", description = "Endpoints de gestiond des
// ressources")
@CrossOrigin("*")
public class RessourceController {
    @Autowired
    private RessourceService ressourceService;
    @Autowired
    private ReservationService reservatoinService;

    @Operation(summary = "Créer une ressource", description = "", tags = "CRUD")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public RessourceResponse postMethodName(@RequestBody RessourceRequest request) {
        return ressourceService.createRessource(request);
    }

    @Operation(summary = "Lister toute les ressources une ressource", description = "", tags = "CRUD")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public List<RessourceResponse> getAllRessource() {
        return ressourceService.getAllRessources();
    }

    @Operation(summary = "Obtenir une ressource", description = "", tags = "CRUD")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get_one/{idRessource}")
    public RessourceResponse getMethodName(@PathVariable(name = "idRessource") String idRessource) {
        return ressourceService.getOneRessourceById(idRessource);
    }

    @Operation(summary = "Mettre à jour une ressource", description = "", tags = "CRUD")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/update/{idRessource}")
    public RessourceResponse updateARessource(@PathVariable(name = "idRessource") String idRessource,
            @RequestBody RessourceRequest request) {
        return ressourceService.updateRessource(idRessource, request);
    }

    @Operation(summary = "Supprime une ressource", description = "", tags = "CRUD")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/delete/{idRessource}")
    public String deleteARessource(@PathVariable(name = "idRessource") String idRessource) {
        return ressourceService.deleteRessource(idRessource);
    }

    @Operation(summary = "Changer de propriétaire", description = "", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{idRessource}/owner/{idOwner}")
    public RessourceResponse transfertOwner(@PathVariable(name = "idRessource") String idRessource,
            @PathVariable(name = "idOwner") String idOwner) {
        return ressourceService.changeOwner(idRessource, idOwner);
    }

    @Operation(summary = "Rendre disponible une ressource", description = "", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{idRessource}/available")
    public RessourceResponse enableRessource(@PathVariable(name = "idRessource") String idRessource) {
        return ressourceService.enableRessource(idRessource);
    }

    @Operation(summary = "Rendre indisponible une ressource", description = "", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{idRessource}/unavailable")
    public RessourceResponse disableRessource(@PathVariable(name = "idRessource") String idRessource) {
        return ressourceService.disableRessource(idRessource);
    }

    @Operation(summary = "Commander une ressource", description = "", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{idRessource}/command/{quantity}")
    public RessourceResponse commandRessource(@PathVariable(name = "idRessource") String idRessource,
            @PathVariable(name = "quantity") Integer quantity) {
        return ressourceService.commandRessource(idRessource, quantity);
    }

    @Operation(summary = "Annuler la commande d'une ressource", description = "", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{idRessource}/cancel_command/{quantity}")
    public RessourceResponse cancelCommandRessource(@PathVariable(name = "idRessource") String idRessource,
            @PathVariable(name = "quantity") Integer quantity) {
        return ressourceService.cancelCommandRessource(idRessource, quantity);
    }

    @Operation(summary = "Liste des ressource disponible", description = "", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/available")
    public List<RessourceResponse>  availableRessource() {
        return ressourceService.availableRessources(Etat.AVAILABLE);
    }
    @Operation(summary = "Liste des ressource disponible", description = "", tags = "Actions Suplémentaire")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/available")
    public List<RessourceResponse>  unavailableRessource() {
        return ressourceService.availableRessources(Etat.UNAVAILABLE);
    }

    @Operation(summary = "Reserver une ressource", description = "", tags = "Reservation")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{idRessource}/reserve/{idReserver}")
    public Reservation reserve(@PathVariable(name = "idRessource") String idRessource,
            @PathVariable(name = "idReserver") String idReserver, @RequestParam("quantity") Integer quantity) {
        return reservatoinService.reserveRessource(idRessource, idReserver,quantity);
    }
}
