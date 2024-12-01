package com.yowyob.gestion_ressources.domain.model;

import java.math.BigDecimal;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("ressources")
public class Ressource {
    @PrimaryKey
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
    private Etat state;
}
