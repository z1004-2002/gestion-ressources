package com.yowyob.gestion_ressources.api.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.yowyob.gestion_ressources.application.dto.ImageDto;
import com.yowyob.gestion_ressources.application.services.ImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/v1/image", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Image")
@CrossOrigin("*")
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PutMapping(path = "/ressource/{ressource_id}/add")
    @Operation(summary = "Charge une liste d'image pour une liste pour une ressource")
    public List<ImageDto> submitImage(@RequestParam("images") MultipartFile[] file, @PathVariable("ressource_id") UUID ressource_id) {

        List<ImageDto> images = new ArrayList<>();

        for (MultipartFile image : file) {
            log.info("Controller save Image ({})", image);
            images.add(imageService.uploadImage(image, ressource_id));
        }
        return images;
    }

    @GetMapping(path = "/{id}")
	@Operation(summary = "Récupérer une image par son id")
	public ImageDto getImageById(@PathVariable("id") Long id) {
		log.info("Controller: Fetching Image by Id {}", id);
		return imageService.getImageById(id);
	}

	@GetMapping("/download/{imageName:.+}")
	@Operation(summary = "Télecharger une image")
	public ResponseEntity<Resource> downloadProfileImage(@PathVariable String imageName,
			HttpServletRequest request) {
		Resource resource = imageService.loadImageByFilename(imageName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			System.out.println("Could Not Determine file ");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
	}

}
