package com.trkgrn_theomer.cheapspring.api.controller;

import com.trkgrn_theomer.cheapspring.api.model.concretes.Image;
import com.trkgrn_theomer.cheapspring.api.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        return ResponseEntity.ok(this.imageService.uploadImage(file));
    }

    @GetMapping(path = { "/get/{imageId}" })
    public Image getImage(@PathVariable("imageId") Long imageId) {
        return this.imageService.getImage(imageId);
    }
}
