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
import com.yowyob.gestion_ressources.infrastructure.persistence.repository.ImageRepository;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;

    private Path fileStorageLocationProduct;
    private String dir = "/src/main/resources/image";

    public ImageService() {
        super();
        this.fileStorageLocationProduct = Paths
                .get(System.getProperty("user.dir") + dir).toAbsolutePath()
                .normalize();
        try {
            Files.createDirectories(this.fileStorageLocationProduct);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory to upload.");
        }
    }

    public List<ImageDto> getImageByRessource(String idRessource) {
        return imageRepository.findByIdRessource(idRessource).stream()
                .map(this::imageToImageDto)
                .toList();
    }

    public ImageDto getImageById(String id_image) {
        return imageToImageDto(imageRepository.findById(id_image).get());
    }

    public ImageDto uploadImage(MultipartFile file, String idRessource) {

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

            return imageToImageDto(
                    imageRepository.save(
                            Image.builder()
                                    .id(UUID.randomUUID().toString())
                                    .name(prefix + fileName)
                                    .path(imageDownloadUri)
                                    .idRessource(idRessource)
                                    .size(file.getSize())
                                    .fileType(file.getContentType())
                                    .build()));

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

    public void deleteById(String id) {
        imageRepository.deleteById(id);
    }

}
