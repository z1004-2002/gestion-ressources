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
    private String domainKey;
    private Integer quantity;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;
    private String idOwner;
    private Integer numberUsage;
    private boolean transferable;
    private Integer maxReservation;
}
