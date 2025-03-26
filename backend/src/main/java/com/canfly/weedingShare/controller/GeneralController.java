package com.canfly.weedingShare.controller;

import com.canfly.weedingShare.model.Post;
import com.canfly.weedingShare.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*") // Permettre l'accès depuis le frontend
public class GeneralController {
    private final PostService postService;
    private static final String IMAGE_UPLOAD_DIR = "uploads/";

    public GeneralController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<Post> addPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "comment", required = false) String comment) {
        try {
            // Assurez-vous que le dossier d'upload existe
            File uploadDir = new File(IMAGE_UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Obtenir le nom du fichier sans extension et son extension
            String originalFilename = image.getOriginalFilename();
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));

            // Construire le chemin de l'image avec vérification d'existence
            String imagePath = IMAGE_UPLOAD_DIR + originalFilename;
            File destinationFile = new File(imagePath);
            int counter = 1;

            while (destinationFile.exists()) {
                String newFileName = baseName + "(" + counter + ")" + extension;
                imagePath = IMAGE_UPLOAD_DIR + newFileName;
                destinationFile = new File(imagePath);
                counter++;
            }

            image.transferTo(Paths.get(imagePath));
            // Ajouter le post dans la "base de données" JSON
            Post newPost = postService.addPost(imagePath, comment);
            return ResponseEntity.ok(newPost);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Endpoint pour supprimer un post par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id) {
        boolean isDeleted = postService.deletePost(id);
        Map<String, String> response = new HashMap<>();
        if (isDeleted) {
            response.put("message", "Post supprimé avec succès");
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Post introuvable");
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
