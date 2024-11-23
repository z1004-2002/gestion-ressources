package com.yowyob.gestion_ressources.application.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RessourceRequest {
    private String id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal buying_price;
    private BigDecimal selling_price;
    private String id_owner;
    private Integer number_usage;
    private boolean transferable;
    private Integer max_reservation;
    // List<ImageDto> images;
}
