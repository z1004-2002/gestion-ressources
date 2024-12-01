package com.yowyob.gestion_ressources.application.dto;

import java.math.BigDecimal;
import java.util.List;

import com.yowyob.gestion_ressources.domain.model.Etat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RessourceResponse {
    private String id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal buyingPrice;
    private BigDecimal sellingPrice;
    private String idOwner;
    private Integer numberUsage;
    private boolean transferable;
    private Integer maxReservation;
    private Etat state;
    private List<ImageDto> images;
}
