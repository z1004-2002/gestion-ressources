package com.yowyob.gestion_ressources.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RessourceRequest {
    private UUID id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal buying_price;
    private BigDecimal selling_price;
    private UUID id_owner;
    private Integer number_usage;
    private boolean transferable;
    private Integer max_reservation;
    // List<ImageDto> images;
}
