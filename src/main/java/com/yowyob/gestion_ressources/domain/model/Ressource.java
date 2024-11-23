package com.yowyob.gestion_ressources.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

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
    private Integer quantity;
    private BigDecimal buying_price;
    private BigDecimal selling_price;
    private String id_owner;
    private Integer number_usage;
    private boolean transferable;
    private Integer max_reservation;
}
