package com.example.filmland.services;

import com.example.filmland.models.Category;
import com.example.filmland.models.Subscriptions;
import com.example.filmland.models.User;
import com.example.filmland.repository.CategoryRepository;
import com.example.filmland.repository.SubscriptionRepository;
import com.example.filmland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Category> findCategoriesByUserUsername(String username) {
        return categoryRepository.findCategoriesByUsersUsername(username);
    }

    public List<Category> findAll(String username) {

        List<Category> availableCategories = new ArrayList<>();

        Iterator itr = categoryRepository.findAll().iterator();

        while (itr.hasNext()) {
            Category category = (Category) itr.next();
            if (!this.findCategoriesByUserUsername(username).contains(category)) {
                availableCategories.add(category);
            }
        }

        return availableCategories;
    }

    public String subScribe(String categoryName, String username) {

        if (!categoryRepository.existsByName(categoryName))
            return "Category doesnt exist";

        if (checkIfAlreadySubscribed(categoryName, username))
            return "Already subscribed to category";

        Category category = categoryRepository.findByName(categoryName);

        this.subscribeUser(categoryName, username);

        return "subscription successful";
    }

    public String shareSubscription(String categoryName, String username, String email) {


        if (!userRepository.existsByEmail(email))
            return "Given email not associated with user";

        if (checkIfAlreadySubscribed(categoryName, username))
            return "Already subscribed to category";

        if (checkIfAlreadySubscribed(categoryName,
                userRepository.findByEmail(email).get().getUsername()))
            return "Friend already subscribed to category";

        Category category = categoryRepository.findByName(categoryName);

        double price = category.getPrice() / 2;

        if (!checkIfSufficientBalance(price, username) ||
                !checkIfSufficientBalance(price, userRepository.findByEmail(email).get().getUsername())) {
            return "Insufficient balance";
        }

        this.subscribeUser(categoryName, username);
        this.subscribeUser(categoryName, userRepository.findByEmail(email).get().getUsername());

        this.updateUserBalance(price, username);
        this.updateUserBalance(price, userRepository.findByEmail(email).get().getUsername());

        return "subscription successful";

    }

    private boolean checkIfAlreadySubscribed(String categoryName, String username)
    {
        if (this.findAll(username).isEmpty())
            return true;

        List<Category> categories = this.findCategoriesByUserUsername(username);

        return categories.stream()
                .map(Category::getName)
                .anyMatch(categoryName::equals);
    }

    private boolean checkIfSufficientBalance(double amount, String username)
    {
        double balance = userRepository.findByUsername(username).get().getBalance();

        return (balance > amount);
    }

    private void subscribeUser(String categoryName, String username)
    {
        Category category = categoryRepository.findByName(categoryName);

        userRepository.findByUsername(username).ifPresent(user -> {
            Subscriptions subscriptions = new Subscriptions();
            subscriptions.setCategory(category);
            subscriptions.setUser(user);
            subscriptions.setStartDate(Instant.now());
            subscriptionRepository.save(subscriptions);
        });
    }

    private void updateUserBalance(double price, String username) {

        User user = userRepository.findByUsername(username).get();
        user.setBalance(user.getBalance() - price);
        userRepository.save(user);
    }


}
