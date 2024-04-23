package com.foodtruck.foodtruck.service;

import java.util.List;

import com.foodtruck.foodtruck.entity.FoodtruckEntity;

public interface FoodTruckService {
    public FoodtruckEntity saveNewFoodTruck(FoodtruckEntity foodtruckEntity);

    public FoodtruckEntity findFoodTruckByEmail(String email);

    public FoodtruckEntity updateFoodTruck(FoodtruckEntity foodtruckEntity);

    public List<FoodtruckEntity> getAllFoodTrucksNearMe();
}
