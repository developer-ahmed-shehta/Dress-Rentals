package com.weddingRental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")  // Handles requests to localhost:8080
    public String redirectToDressesList() {
        return "redirect:/dresses/list";  // Redirects to the dresses list page
    }
}
