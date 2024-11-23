package com.yowyob.gestion_ressources.domain.services;

import java.math.BigDecimal;
import java.util.UUID;

import com.yowyob.gestion_ressources.domain.model.Etat;
import com.yowyob.gestion_ressources.domain.model.Ressource;

public class RessourceFactory {
    public static Ressource create(String name, String description, Integer quantity, BigDecimal buying_price,BigDecimal selling_price, String id_owner,
            Integer number_usage, boolean transferable,Integer max_reservation) {
        return Ressource.builder()
                .id(UUID.randomUUID().toString())  // Generate a unique ID for each resource automatically
                .name(name)
                .description(description)
                .quantity(quantity)
                .buying_price(buying_price)
                .selling_price(selling_price)
                .id_owner(id_owner)
                .number_usage(number_usage)
                .transferable(transferable)
                .state(Etat.UNAVAILABLE)
                .max_reservation(max_reservation)
                .build();
    }
}
