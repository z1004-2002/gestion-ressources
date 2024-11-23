package com.yowyob.gestion_ressources.domain.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("reservation")
@Builder
public class Reservation {
    @PrimaryKey
    private ReservationKey primaryKey;
    private Integer quantity;
}
