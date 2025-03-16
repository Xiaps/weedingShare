package com.canfly.weedingShare.service;

import com.canfly.weedingShare.model.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    private static final String FILE_PATH = "database/posts.json";
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

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
