package com.example.filmland.repository;

import com.example.filmland.models.Category;
import com.example.filmland.models.Subscriptions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscriptions, Long> {
    List<Subscriptions> findSubscriptionsByUserUsername(String username);
}