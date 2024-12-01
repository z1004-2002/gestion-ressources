package com.yowyob.gestion_ressources.api.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/v1/image", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Images", description = "Manages images for the resouces")
@CrossOrigin("*")
@Slf4j
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PutMapping(path = "/ressource/{idRessource}/add")
    @Operation(summary = "Get a list of Id for a ressource")
    public List<ImageDto> submitImage(@RequestParam("images") MultipartFile[] file, @PathVariable("idRessource") String idRessource) {
        List<ImageDto> images = new ArrayList<>();
        for (MultipartFile image : file) {
            log.info("Controller save Image ({})", image);
            images.add(imageService.uploadImage(image, idRessource));
        }
        return images;
    }

    @GetMapping(path = "/{id}")
	@Operation(summary = "Get an image", description = "Get an image by his id")
	public ImageDto getImageById(@PathVariable("id") String id) {
		log.info("Controller: Fetching Image by Id {}", id);
		return imageService.getImageById(id);
	}

	@GetMapping("/download/{imageName:.+}")
	@Operation(summary = "Download Image")
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
