package com.weddingRental.controller;

import com.weddingRental.service.DressService;
import com.weddingRental.model.Dress;
import com.weddingRental.helper.Pair;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller; // Important: Use @Controller for Thymeleaf
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl; // For creating empty pages
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

// Controller to handle HTTP requests for Dress entity
@RestController
@RequestMapping("/dresses") // Base URL for all dress-related API endpoints
public class DressController {

    private final DressService dressService;
    private static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/dress_images/"; // Or configure it


    @Autowired // Spring will automatically inject the DressService bean here
    public DressController(DressService dressService) {
        this.dressService = dressService;
    }

    // Get all dresses
    @GetMapping("/list")
    public ModelAndView getAllDresses(@PageableDefault(size = 15) Pageable pageable) {
        ModelAndView mav = new ModelAndView("dress_list");
        Page<Dress> dresses = dressService.getAllDresses(pageable);

        if (dresses.isEmpty()) {
            // Create an empty Page if no dresses are found
            dresses = new PageImpl<>(Collections.emptyList(), pageable, 0); // Important!
        }      

        mav.addObject("dresses", dresses.getContent());
        mav.addObject("page", dresses); // Always add the Page object
        return mav;
    }

    // Add a new dress
    @PostMapping("/set")
    public ModelAndView addDress(@ModelAttribute("dress") Dress dress, @RequestParam("photo") MultipartFile file, Model model) {
            ModelAndView mav = new ModelAndView("add_dress"); // Correct view name
    
            try {
                Pair<Boolean,String> boolStringPair = handleFileUpload(file);
                Boolean fileCheck = boolStringPair.getFirst();
                String fileMessageOrContent = boolStringPair.getSecond();
    
                if (fileCheck) {
                    dress.setPhotoPath(fileMessageOrContent);
                } else {
                    mav.addObject("dress", dress); // Re-populate the form
                    mav.addObject("messge", fileMessageOrContent);
                    return mav; // Return immediately if there's an error
                }
    
                dressService.addDress(dress); // Your service to save to the database
                mav.addObject("message", "Dress added successfully!");
                mav.addObject("dress", new Dress()); // Reset the form after successful save
    
            } catch (IOException e) {
                model.addAttribute("message", "Error uploading file: " + e.getMessage());
                e.printStackTrace(); // Keep this for debugging
            } catch (Exception e) {
                model.addAttribute("message", "An unexpected error occurred: " + e.getMessage());
                e.printStackTrace(); // Keep this for debugging
            }
            return mav;
        }

    @GetMapping("/edit/{id}") // Corrected mapping for edit
    public ModelAndView showEditDressForm(@PathVariable Long id) {
        
        Dress dress = dressService.getDressById(id).orElse(null);
        if (dress == null) {
            // Handle dress not found (e.g., redirect, show error)
            return new ModelAndView("redirect:/dresses/list"); // Example: redirect to list
        }
        ModelAndView mav = new ModelAndView("edit_dress");
        mav.addObject("dress", dress);
        return mav; // Template name for editing
    }

    // Add a new dress
    @GetMapping("/add")
    public ModelAndView showAddDressForm() {
        ModelAndView mav = new ModelAndView("add_dress"); // Template name
        mav.addObject("dress", new Dress()); // Add data to model
        return mav;
    }


    @PostMapping("/save")
    public ModelAndView saveDress(@ModelAttribute("dress") Dress dress,
                                  @RequestParam("photo") MultipartFile file,
                                  @RequestParam(value = "originalPhotoPath", required = false) String originalPhotoPath ,
                                  RedirectAttributes redirectAttributes) {

        ModelAndView mav = new ModelAndView("edit_dress"); // Correct view name

        try {
            if (!file.isEmpty()) { // Only handle upload if a new file is present
                Pair<Boolean, String> result = handleFileUpload(file);
                if (result.getFirst()) {
                    dress.setPhotoPath(result.getSecond());
                } else {
                    mav.addObject("dress", dress);
                    mav.addObject("message", result.getSecond()); // Or result.getSecond() if handleFileUpload adds to the model
                    return mav;
                }
            } // If file is empty, dress.photoPath remains unchanged
            else{
                dress.setPhotoPath(originalPhotoPath);
            }
                
            dressService.saveDress(dress); // Your service to save to the database

            redirectAttributes.addFlashAttribute("message", "Dress saved successfully!");
            return new ModelAndView("redirect:/dresses/list"); // Redirect after successful save

        } catch (IOException e) {
            mav.addObject("message", "Error uploading file: " + e.getMessage());
            e.printStackTrace(); // Keep this for debugging
            mav.addObject("dress", dress); // Re-populate the form
            return mav;
        } catch (Exception e) {
            mav.addObject("message", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace(); // Keep this for debugging
            mav.addObject("dress", dress); // Re-populate the form
            return mav;
        }
    }


    // Delete a dress by ID
    @PostMapping("/delete/{id}")
    public ModelAndView deleteDress(@PathVariable Long id,RedirectAttributes redirectAttributes) {
        boolean deleted = dressService.deleteDress(id);
        redirectAttributes.addFlashAttribute("message", "Dress saved successfully!");
        return new ModelAndView("redirect:/dresses/list"); // Redirect after successful save
    }

    private Pair<Boolean,String> handleFileUpload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new Pair<>(false, " No file uploaded");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return new Pair<>(false, "Invalid file type. Please upload an image.");
        }

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isBlank() || !originalFileName.contains(".")) {
            return new Pair<>(false, "Invalid file name. Please upload a valid image.");
        }

        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path fileNameAndPath = uploadPath.resolve(fileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, fileNameAndPath, StandardCopyOption.REPLACE_EXISTING);
        }
        return new Pair<>(true,"/dress_images/" + fileName);
        
    }

}
