package com.example.filmland.payload.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ShareRequest {

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min = 3, max = 20)
    private String subscribedCategory;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubscribedCategory() {
        return subscribedCategory;
    }

    public void setSubscribedCategory(String subscribedCategory) {
        this.subscribedCategory = subscribedCategory;
    }
}