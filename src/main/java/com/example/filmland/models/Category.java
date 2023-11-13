package com.example.filmland.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @NotBlank
    @Size(max = 50)
    @Email
    private String name;

    @NotBlank
    private double price;

    @NotBlank
    private int availableContent;

    @ManyToMany
    @JoinTable(
            name = "user_category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    public Category() {}

    public Category(long id, String name, double price, int availableContent) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableContent = availableContent;
    }

    public int getAvailableContent() {
        return availableContent;
    }

    public void setAvailableContent(int availableContent) {
        this.availableContent = availableContent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addUser(User user) {
        this.users.add(user);
        user.getCategories().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
