package com.yowyob.gestion_ressources.application.services;

import java.util.List;
import java.util.UUID;

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

    public RessourceResponse createRessource(RessourceRequest request, UUID domainKey) {
        Ressource ressource = RessourceFactory.create(request, domainKey);
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public RessourceResponse getOneRessourceById(UUID id) {
        Ressource ressource = ressourceRepository.findById(id.toString()).get();
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        List<ImageDto> imageDtos = imageServive.getImageByRessource(id.toString());

        return RessourceResponse.builder()
                .id(ressource.getId())
                .name(ressource.getName())
                .description(ressource.getDescription())
                .quantity(ressource.getQuantity())
                .buyingPrice(ressource.getBuyingPrice())
                .sellingPrice(ressource.getSellingPrice())
                .idOwner(ressource.getIdOwner())
                .numberUsage(ressource.getNumberUsage())
                .transferable(ressource.isTransferable())
                .maxReservation(ressource.getMaxReservation())
                .state(ressource.getState())
                .images(imageDtos)
                .build();
    }

    public List<RessourceResponse> getAllRessources() {
        return ressourceRepository.findAll().stream().map(this::ressourceToRessourceResponse).toList();
    }

    public List<RessourceResponse> getAllRessources(UUID domainKey) {
        return ressourceRepository.findByDomainKey(domainKey.toString()).stream()
                .map(this::ressourceToRessourceResponse).toList();
    }

    public List<RessourceResponse> getRessourcesByIdOwner(UUID ownerId) {
        return ressourceRepository.findByOwnerId(ownerId.toString()).stream()
                .map(this::ressourceToRessourceResponse).toList();
    }

    public RessourceResponse updateRessource(UUID id, RessourceRequest request) {
        Ressource ressource = ressourceRepository.findById(id.toString()).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressource.setName(request.getName());
        ressource.setDescription(request.getDescription());
        ressource.setQuantity(request.getQuantity());
        ressource.setBuyingPrice(request.getBuyingPrice());
        ressource.setSellingPrice(request.getSellingPrice());
        ressource.setNumberUsage(request.getNumberUsage());
        ressource.setTransferable(request.isTransferable());
        ressource.setMaxReservation(request.getMaxReservation());
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public String deleteRessource(UUID id) {
        Ressource ressource = ressourceRepository.findById(id.toString()).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressourceRepository.deleteById(id.toString());
        return "Resource deleted";
    }

    public RessourceResponse changeOwner(UUID idRessource, UUID idOwner) {
        Ressource ressource = ressourceRepository.findById(idRessource.toString()).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressource.setIdOwner(idOwner.toString());
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public RessourceResponse enableRessource(UUID idRessource) {
        Ressource ressource = ressourceRepository.findById(idRessource.toString()).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressource.setState(Etat.AVAILABLE);
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    public RessourceResponse disableRessource(UUID idRessource) {
        Ressource ressource = ressourceRepository.findById(idRessource.toString()).orElse(null);
        if (ressource == null) {
            throw new NotFoundException("No such resource");
        }
        ressource.setState(Etat.UNAVAILABLE);
        return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    }

    // public RessourceResponse commandRessource(UUID idRessource, int quantity) {
    //     Ressource ressource = ressourceRepository.findById(idRessource.toString()).orElse(null);
    //     if (ressource == null) {
    //         throw new NotFoundException("No such resource");
    //     }
    //     if (ressource.getState() == Etat.UNAVAILABLE) {
    //         throw new IllegalStateException("Cette ressource est indisponible");
    //     }
    //     if (quantity > ressource.getQuantity() || quantity < 0) {
    //         throw new IllegalStateException("Cette ressource a déjà atteint son maximum de réservations");
    //     }
    //     ressource.setNumberUsage(ressource.getNumberUsage() + quantity);
    //     ressource.setQuantity(ressource.getQuantity() - quantity);
    //     return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    // }

    // public RessourceResponse cancelCommandRessource(String idRessource, Integer quantity) {
    //     Ressource ressource = ressourceRepository.findById(idRessource).orElse(null);
    //     if (ressource == null) {
    //         throw new NotFoundException("No such resource");
    //     }
    //     ressource.setNumberUsage(ressource.getNumberUsage() - quantity);
    //     ressource.setQuantity(ressource.getQuantity() + quantity);
    //     return ressourceToRessourceResponse(ressourceRepository.save(ressource));
    // }

    public List<RessourceResponse> availableRessources(Etat available) {
        return ressourceRepository.findByState(available).stream()
                .map(this::ressourceToRessourceResponse).toList();
    }

    public List<RessourceResponse> unavailableRessources(Etat unavailable) {
        return ressourceRepository.findByState(unavailable).stream()
                .map(this::ressourceToRessourceResponse).toList();
    }

    public List<RessourceResponse> availableRessources(UUID domainKey, Etat available) {
        return ressourceRepository.findByState(available).stream()
                .map(this::ressourceToRessourceResponse).toList();
    }

    public List<RessourceResponse> unavailableRessources(UUID domainKey, Etat unavailable) {
        return ressourceRepository.findByDomainKeyState(domainKey.toString(), unavailable).stream()
                .map(this::ressourceToRessourceResponse).toList();
    }

    private RessourceResponse ressourceToRessourceResponse(Ressource ressource) {
        return RessourceResponse.builder()
                .id(ressource.getId())
                .name(ressource.getName())
                .description(ressource.getDescription())
                .quantity(ressource.getQuantity())
                .buyingPrice(ressource.getBuyingPrice())
                .sellingPrice(ressource.getSellingPrice())
                .idOwner(ressource.getIdOwner())
                .numberUsage(ressource.getNumberUsage())
                .transferable(ressource.isTransferable())
                .maxReservation(ressource.getMaxReservation())
                .state(ressource.getState())
                .images(imageServive.getImageByRessource(ressource.getId()))
                .build();
    }


}
