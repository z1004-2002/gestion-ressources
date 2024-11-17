package com.yowyob.gestion_ressources.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.yowyob.gestion_ressources.application.dto.ImageDto;
import com.yowyob.gestion_ressources.application.dto.RessourceRequest;
import com.yowyob.gestion_ressources.application.dto.RessourceResponse;
import com.yowyob.gestion_ressources.domain.model.Ressource;
import com.yowyob.gestion_ressources.domain.services.RessourceFactory;
import com.yowyob.gestion_ressources.infrastructure.persistence.repository.RessourceRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class RessourceService {

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private ImageService imageServive;

    public RessourceResponse createRessource(RessourceRequest request) {
        Ressource ressource = RessourceFactory.create(
                request.getName(),
                request.getDescription(),
                request.getQuantity(),
                request.getBuying_price(),
                request.getSelling_price(),
                request.getId_owner(),
                request.getNumber_usage(),
                request.isTransferable(),
                request.getMax_reservation());
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public RessourceResponse getOneRessourceById(UUID id) {
        Ressource ressource = ressourceRepository.findById(id).get();
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        List<ImageDto> imageDtos = imageServive.getImageByRessource(id);

        return RessourceResponse.builder()
                .id(ressource.getId())
                .name(ressource.getName())
                .description(ressource.getDescription())
                .quantity(ressource.getQuantity())
                .buying_price(ressource.getBuying_price())
                .selling_price(ressource.getSelling_price())
                .id_owner(ressource.getId_owner())
                .number_usage(ressource.getNumber_usage())
                .transferable(ressource.isTransferable())
                .max_reservation(ressource.getMax_reservation())
                .images(imageDtos)
                .build();
    }

    public List<RessourceResponse> getAllRessources() {
        return ressourceRepository.findAll().stream().map(this::ressourceToRessourceResponse).toList();
    }

    public RessourceResponse updateRessource(UUID id, RessourceRequest request) {
        Ressource ressource = ressourceRepository.findById(id).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressource.setName(request.getName());
        ressource.setDescription(request.getDescription());
        ressource.setQuantity(request.getQuantity());
        ressource.setBuying_price(request.getBuying_price());
        ressource.setSelling_price(request.getSelling_price());
        ressource.setNumber_usage(request.getNumber_usage());
        ressource.setTransferable(request.isTransferable());
        ressource.setMax_reservation(request.getMax_reservation());
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public String deleteRessource(UUID id) {
        Ressource ressource = ressourceRepository.findById(id).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressourceRepository.deleteById(id);
        return "Resource deleted";
    }

    private RessourceResponse ressourceToRessourceResponse(Ressource ressource) {
        return RessourceResponse.builder()
                .id(ressource.getId())
                .name(ressource.getName())
                .description(ressource.getDescription())
                .quantity(ressource.getQuantity())
                .buying_price(ressource.getBuying_price())
                .selling_price(ressource.getSelling_price())
                .id_owner(ressource.getId_owner())
                .number_usage(ressource.getNumber_usage())
                .transferable(ressource.isTransferable())
                .max_reservation(ressource.getMax_reservation())
                .images(imageServive.getImageByRessource(ressource.getId()))
                .build();
    }
}
