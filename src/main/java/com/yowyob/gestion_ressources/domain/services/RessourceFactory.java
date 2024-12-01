package com.yowyob.gestion_ressources.domain.services;

import java.util.UUID;

import com.yowyob.gestion_ressources.application.dto.RessourceRequest;
import com.yowyob.gestion_ressources.domain.model.Etat;
import com.yowyob.gestion_ressources.domain.model.Ressource;

public class RessourceFactory {
    public static Ressource create(RessourceRequest request, UUID domainKey) {
        return Ressource.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .domainKey(domainKey.toString())
                .quantity(request.getQuantity())
                .buyingPrice(request.getBuyingPrice())
                .sellingPrice(request.getSellingPrice())
                .idOwner(request.getIdOwner())
                .numberUsage(request.getNumberUsage())
                .transferable(request.isTransferable())
                .state(Etat.UNAVAILABLE)
                .maxReservation(request.getMaxReservation())
                .build();
    }
}
