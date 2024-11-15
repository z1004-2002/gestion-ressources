package com.yowyob.gestion_ressources.domain.services;

import java.math.BigDecimal;
import java.util.List;

import com.yowyob.gestion_ressources.domain.model.Image;
import com.yowyob.gestion_ressources.domain.model.Ressource;

public class RessourceFactory {
    public static Ressource create(String name, String description, Integer quantity, BigDecimal price, String id_owner,
            Integer nomber_usage, boolean is_transferable, List<Image> images) {
        return Ressource.builder()
                .name(name)
                .description(description)
                .quantity(quantity)
                .price(price)
                .id_owner(id_owner)
                .nomber_usage(nomber_usage)
                .is_transferable(is_transferable)
                .images(images)
                .build();
    }
}
