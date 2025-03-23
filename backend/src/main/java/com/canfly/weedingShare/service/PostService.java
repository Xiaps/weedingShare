package com.canfly.weedingShare.service;

import com.canfly.weedingShare.model.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.file.Files;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private static final String FILE_PATH = "database/posts.json";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public PostService() {

        Path databaseDir = Paths.get("database");

        // Crée le dossier "Database" s'il n'existe pas
        if (!Files.exists(databaseDir)) {
            try {
                Files.createDirectories(databaseDir);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Impossible de créer le dossier Database");
            }
        }
    }

    public List<Post> getAllPosts() {
        return readPostsFromFile();
    }

    public Post addPost(String imagePath, String comment) {
        List<Post> posts = readPostsFromFile();
        Post newPost = new Post(UUID.randomUUID().toString(), imagePath, comment);
        posts.add(newPost);
        writePostsToFile(posts);
        return newPost;
    }

    // Méthode pour supprimer un post en fonction de son ID
    public boolean deletePost(String postId) {
        List<Post> posts = readPostsFromFile();
        // Filtrer les posts pour exclure celui avec l'ID spécifié
        List<Post> updatedPosts = posts.stream()
                .filter(post -> !post.getId().equals(postId))
                .collect(Collectors.toList());

        // Si la taille de la liste a changé, cela signifie qu'un post a été supprimé
        if (updatedPosts.size() < posts.size()) {
            writePostsToFile(updatedPosts); // Sauvegarder la nouvelle liste dans le fichier
            return true; // Suppression réussie
        }

        return false; // Aucun post trouvé avec cet ID
    }

    private List<Post> readPostsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(file, new TypeReference<List<Post>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writePostsToFile(List<Post> posts) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), posts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
