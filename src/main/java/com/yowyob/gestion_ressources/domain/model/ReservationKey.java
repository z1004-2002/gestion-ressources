package com.yowyob.gestion_ressources.domain.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationKey {
    @PrimaryKeyColumn(name = "idReserver", type = PrimaryKeyType.PARTITIONED)
    private String idReserver;
    @PrimaryKeyColumn(name = "idRessource", type = PrimaryKeyType.CLUSTERED)
    private String idRessource;
}
