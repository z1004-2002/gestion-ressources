package com.yowyob.gestion_ressources.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yowyob.gestion_ressources.application.dto.ImageDto;
import com.yowyob.gestion_ressources.domain.model.Image;
import com.yowyob.gestion_ressources.infrastructure.persistence.repository.ImageRepository;

@Service
public class ImageServive {
    @Autowired
    private ImageRepository imageRepository;

    public ImageDto imageToImageDto(Image image){
        return ImageDto.builder()
               .id(image.getId())
               .path(image.getPath())
               .build();
    }

    public List<ImageDto> getImageByRessource(UUID id_ressource){
        return imageRepository.findByRessourceId(id_ressource).stream()
                .map(this::imageToImageDto)
                .toList();
    }

}
