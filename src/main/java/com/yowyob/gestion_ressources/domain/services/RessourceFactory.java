package com.yowyob.gestion_ressources.domain.services;

import java.math.BigDecimal;
import java.util.UUID;
import com.yowyob.gestion_ressources.domain.model.Ressource;

public class RessourceFactory {
    public static Ressource create(String name, String description, Integer quantity, BigDecimal buying_price,BigDecimal selling_price, UUID id_owner,
            Integer number_usage, boolean transferable,Integer max_reservation) {
        return Ressource.builder()
                .name(name)
                .description(description)
                .quantity(quantity)
                .buying_price(buying_price)
                .selling_price(selling_price)
                .id_owner(id_owner)
                .number_usage(number_usage)
                .transferable(transferable)
                .max_reservation(max_reservation)
                .build();
    }
}
