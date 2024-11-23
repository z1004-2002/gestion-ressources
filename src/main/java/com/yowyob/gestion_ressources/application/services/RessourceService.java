package com.yowyob.gestion_ressources.application.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.yowyob.gestion_ressources.application.dto.ImageDto;
import com.yowyob.gestion_ressources.application.dto.RessourceRequest;
import com.yowyob.gestion_ressources.application.dto.RessourceResponse;
import com.yowyob.gestion_ressources.domain.model.Etat;
import com.yowyob.gestion_ressources.domain.model.Ressource;
import com.yowyob.gestion_ressources.domain.services.RessourceFactory;
import com.yowyob.gestion_ressources.infrastructure.persistence.repository.RessourceRepository;


@Service
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

    public RessourceResponse getOneRessourceById(String id) {
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
                .state(ressource.getState())
                .images(imageDtos)
                .build();
    }

    public List<RessourceResponse> getAllRessources() {
        return ressourceRepository.findAll().stream().map(this::ressourceToRessourceResponse).toList();
    }

    public RessourceResponse updateRessource(String id, RessourceRequest request) {
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

    public String deleteRessource(String id) {
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
                .state(ressource.getState())
                .images(imageServive.getImageByRessource(ressource.getId()))
                .build();
    }

    public RessourceResponse changeOwner(String idRessource, String idOwner) {
        Ressource ressource = ressourceRepository.findById(idRessource).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressource.setId_owner(idOwner);
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public RessourceResponse enableRessource(String idRessource) {
        Ressource ressource = ressourceRepository.findById(idRessource).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressource.setState(Etat.AVAILABLE);
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public RessourceResponse disableRessource(String idRessource) {
        Ressource ressource = ressourceRepository.findById(idRessource).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressource.setState(Etat.UNAVAILABLE);
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public RessourceResponse commandRessource(String idRessource,int quantity) {
        Ressource ressource = ressourceRepository.findById(idRessource).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        if (ressource.getState() == Etat.UNAVAILABLE) {
            throw new IllegalStateException("Cette ressource est indisponible");
        }
        if (quantity > ressource.getQuantity() || quantity < 0) {
            throw new IllegalStateException("Cette ressource a déjà atteint son maximum de réservations");
        }
        ressource.setNumber_usage(ressource.getNumber_usage() + quantity);
        ressource.setQuantity(ressource.getQuantity()-quantity);
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public RessourceResponse cancelCommandRessource(String idRessource, Integer quantity) {
        Ressource ressource = ressourceRepository.findById(idRessource).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressource.setNumber_usage(ressource.getNumber_usage() - quantity);
        ressource.setQuantity(ressource.getQuantity()+quantity);
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public List<RessourceResponse> availableRessources(Etat available) {
        return ressourceRepository.findByState(available).stream()
               .map(this::ressourceToRessourceResponse).toList();
    }
    public List<RessourceResponse> unavailableRessources(Etat available) {
        return ressourceRepository.findByState(available).stream()
               .map(this::ressourceToRessourceResponse).toList();
    }
}
