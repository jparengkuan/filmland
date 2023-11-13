package com.example.filmland.payload.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class SubscribeRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String availableCategory;

    public String getAvailableCategory() {
        return availableCategory;
    }

    public void setAvailableCategory(String availableCategory) {
        this.availableCategory = availableCategory;
    }
}