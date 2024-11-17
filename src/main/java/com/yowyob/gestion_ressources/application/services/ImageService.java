package com.yowyob.gestion_ressources.application.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yowyob.gestion_ressources.application.dto.ImageDto;
import com.yowyob.gestion_ressources.domain.model.Image;
import com.yowyob.gestion_ressources.domain.model.Ressource;
import com.yowyob.gestion_ressources.infrastructure.persistence.repository.ImageRepository;
import com.yowyob.gestion_ressources.infrastructure.persistence.repository.RessourceRepository;

import lombok.NoArgsConstructor;


@Service
@NoArgsConstructor
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private RessourceRepository ressourceRepository;
    
    private Path fileStorageLocationProduct;
    private String dir = "/src/main/resources/image";

    @Autowired(required=true)
    public ImageService(
            ImageRepository fileRepository,
            RessourceRepository ressourceRepository) {
        super();
        this.ressourceRepository = ressourceRepository;
        this.fileStorageLocationProduct = Paths
                .get(System.getProperty("user.dir")+dir).toAbsolutePath()
                .normalize();
        this.ressourceRepository = ressourceRepository;

        try {
            Files.createDirectories(this.fileStorageLocationProduct);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory to upload.");
        }
    }

    public List<ImageDto> getImageByRessource(UUID id_ressource) {
        return imageRepository.findByRessource(ressourceRepository.findById(id_ressource).get()).stream()
                .map(this::imageToImageDto)
                .toList();
    }

    public ImageDto getImageById(Long image_id) {
        return imageToImageDto(imageRepository.findById(image_id).get());
    }

    public ImageDto uploadImage(MultipartFile file, UUID ressource_id) {

        // GENERATION OF VARCHAR

        String prefix = "yowyob_";
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder s = new StringBuilder(50);
        for (int i = 0; i < 50; i++) {
            int index = (int) (str.length() * Math.random());
            s.append(str.charAt(index));
        }
        prefix = String.valueOf(s);

        // END OF GENERATION

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            Path targetLocation = this.fileStorageLocationProduct.resolve(prefix + fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String imageDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(System.getProperty("user.dir") + "/src/main/resources/image/")
                    .path(prefix + fileName)
                    .toUriString();

            Ressource r = ressourceRepository.findById(ressource_id).get();

            return imageToImageDto(
                    imageRepository.save(Image.builder().name(prefix + fileName).path(imageDownloadUri).ressource(r)
                            .size(file.getSize()).fileType(file.getContentType()).build()));

        } catch (IOException e) {
            throw new RuntimeException("Could not store file " + prefix + fileName + ". Please try again!", e);
        }
    }

    public Resource loadImageByFilename(String fileName) {
        try {
            Path filePath = this.fileStorageLocationProduct.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Le fichier: " + fileName + " est introuvable");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Le fichier: " + fileName + " est introuvable");
        }
    }

    private ImageDto imageToImageDto(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .name(image.getName())
                .size(image.getSize())
                .fileType(image.getFileType())
                .build();
    }

    public void deleteById(Long id) {
        imageRepository.deleteById(id);
    }

}
