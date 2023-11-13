package com.example.filmland.repository;

import com.example.filmland.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findCategoriesByUsersUsername(String username);

    Category findByName(String name);

    Boolean existsByName(String name);

}