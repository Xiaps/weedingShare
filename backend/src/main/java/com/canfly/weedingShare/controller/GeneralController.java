package com.canfly.weedingShare.controller;

import com.canfly.weedingShare.model.Post;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*") // Permettre l'accès depuis le frontend
public class GeneralController {
    private static final String UPLOAD_DIR = "uploads/";

    public GeneralController() {
        // Crée le dossier d'upload s'il n'existe pas
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (Exception e) {
                throw new RuntimeException("Impossible de créer le dossier d'upload", e);
            }
        }
    }

    @PostMapping("/add")
    public Post addPost(@RequestParam("image") MultipartFile image,
                        @RequestParam(value = "comment", required = false) String comment) {
        try {
            // Génération d'un nom unique pour l'image
            String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Création du post avec l'URL du fichier
            Post newPost = new Post(UUID.randomUUID().toString(), fileName, comment);
            return newPost;

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'upload du fichier", e);
        }
    }
}
