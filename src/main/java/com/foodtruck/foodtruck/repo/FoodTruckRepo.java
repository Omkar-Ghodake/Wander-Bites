package com.foodtruck.foodtruck.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodtruck.foodtruck.entity.FoodtruckEntity;

@Repository
public interface FoodTruckRepo extends JpaRepository<FoodtruckEntity, Long> {
    public FoodtruckEntity findByEmail(String email);
}
