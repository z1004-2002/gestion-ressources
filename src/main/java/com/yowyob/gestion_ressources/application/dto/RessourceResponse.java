package com.yowyob.gestion_ressources.application.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RessourceResponse {
    private String id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private String id_owner;
    private Integer nomber_usage;
    private boolean is_transferable;
    private List<String> images;
}
