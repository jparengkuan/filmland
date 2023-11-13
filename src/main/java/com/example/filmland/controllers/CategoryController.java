package com.example.filmland.controllers;

import com.example.filmland.models.Category;
import com.example.filmland.payload.requests.ShareRequest;
import com.example.filmland.payload.requests.SubscribeRequest;
import com.example.filmland.payload.response.MessageResponse;
import com.example.filmland.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<Map<String, List<Category>>> getCategories() {
        Map<String, List<Category>> response = new HashMap<>();

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        response.put("availableCategories", categoryService.findAll(userDetails.getUsername()));
        response.put("subscribedCategories", categoryService.findCategoriesByUserUsername(userDetails.getUsername()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subScribe(@Valid @RequestBody SubscribeRequest subscribeRequest)
    {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String categoryName = subscribeRequest.getAvailableCategory();
        String username = userDetails.getUsername();

        String response = categoryService.subScribe(categoryName, username);

        return ResponseEntity.ok(new MessageResponse(response));
    }

    @PostMapping("/share")
    public ResponseEntity<?> share(@Valid @RequestBody ShareRequest shareRequest)
    {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String categoryName = shareRequest.getSubscribedCategory();
        String email = shareRequest.getEmail();
        String username = userDetails.getUsername();

        String response = categoryService.shareSubscription(categoryName, username, email);

        return ResponseEntity.ok(new MessageResponse(response));
    }


}