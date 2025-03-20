package com.canfly.weedingShare.service;

import com.canfly.weedingShare.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SlideshowService {

    private final SimpMessagingTemplate messagingTemplate;
    private final PostService postService;
    private int currentIndex = 0;

    @Autowired
    public SlideshowService(SimpMessagingTemplate messagingTemplate, PostService postService) {
        this.messagingTemplate = messagingTemplate;
        this.postService = postService;
    }

    @Scheduled(fixedRate = 5000) // Changement d'image toutes les 5 secondes
    public void updateSlideshow() {
        List<Post> posts = postService.getAllPosts();
        if (posts.isEmpty()) {
            return;
        }

        Post currentPost = posts.get(currentIndex);
        Post nextPost = posts.get((currentIndex + 1) % posts.size()); // Anticiper le prochain

        // Envoyer les 2 posts au frontend
        Map<String, Post> payload = Map.of(
                "currentPost", currentPost,
                "nextPost", nextPost
        );

        messagingTemplate.convertAndSend("/topic/slideshow", payload);

        currentIndex = (currentIndex + 1) % posts.size(); // Avancer dans la liste
    }
}
