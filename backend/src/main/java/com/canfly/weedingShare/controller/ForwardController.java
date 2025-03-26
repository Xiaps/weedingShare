package com.canfly.weedingShare.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwardController {

    @GetMapping("/{path:[^\\.]*}") // Intercepte toutes les routes sauf celles avec une extension (ex: .js, .css)
    public String forward() {
        return "forward:/index.html"; // Redirige vers Angular
    }
}