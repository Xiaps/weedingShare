package com.canfly.weedingShare.controller;

import com.canfly.weedingShare.model.Post;
import com.canfly.weedingShare.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*") // Permettre l'accès depuis le frontend
public class GeneralController {
    private final PostService postService;
    private static final String IMAGE_UPLOAD_DIR = "database/uploads/";

    public GeneralController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @PostMapping("/add")
    public ResponseEntity<Post> addPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "comment", required = false) String comment)
    {
        try {
            // Assurez-vous que le dossier d'upload existe
            File uploadDir = new File(IMAGE_UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Enregistrer l'image sur le disque
            String imagePath = IMAGE_UPLOAD_DIR + image.getOriginalFilename();
            File destinationFile = new File(imagePath);
            image.transferTo(Paths.get(imagePath));

            // Ajouter le post dans la "base de données" JSON
            Post newPost = postService.addPost(imagePath, comment);
            return ResponseEntity.ok(newPost);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
