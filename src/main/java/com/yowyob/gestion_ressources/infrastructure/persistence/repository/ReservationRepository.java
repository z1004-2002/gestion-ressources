package com.yowyob.gestion_ressources.infrastructure.persistence.repository;


import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.yowyob.gestion_ressources.domain.model.Reservation;
import com.yowyob.gestion_ressources.domain.model.ReservationKey;

@Repository
public interface ReservationRepository extends CassandraRepository<Reservation, ReservationKey>{
   
}
