package com.yowyob.gestion_ressources.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yowyob.gestion_ressources.domain.model.Etat;
import com.yowyob.gestion_ressources.domain.model.Reservation;
import com.yowyob.gestion_ressources.domain.model.ReservationKey;
import com.yowyob.gestion_ressources.domain.model.Ressource;
import com.yowyob.gestion_ressources.infrastructure.persistence.repository.ReservationRepository;
import com.yowyob.gestion_ressources.infrastructure.persistence.repository.RessourceRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired 
    private RessourceRepository ressourceRepository;

    public Reservation reserveRessource(String idRessource, String idReserver,int quantity){
        ReservationKey key = ReservationKey.builder().idReserver(idReserver).idRessource(idRessource).build();
        Reservation reservation =  reservationRepository.findById(key).orElse(null);
        if(reservation == null){
            reservation = Reservation.builder().primaryKey(key).quantity(quantity).build();
            Ressource r = ressourceRepository.findById(idRessource).orElse(null);
            r.setState(Etat.RESERVED);
            ressourceRepository.save(r); 
            return reservationRepository.save(reservation);
        }else{
            reservation.setQuantity(reservation.getQuantity() + quantity);
            return reservationRepository.save(reservation);
        }
    }
}
