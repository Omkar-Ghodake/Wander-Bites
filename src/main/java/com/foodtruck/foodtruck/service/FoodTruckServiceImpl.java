package com.foodtruck.foodtruck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodtruck.foodtruck.entity.FoodtruckEntity;
import com.foodtruck.foodtruck.repo.FoodTruckRepo;

@Service
public class FoodTruckServiceImpl implements FoodTruckService {
    @Autowired
    FoodTruckRepo foodTruckRepo;

    @Override
    public FoodtruckEntity saveNewFoodTruck(FoodtruckEntity foodtruckEntity) {
        return foodTruckRepo.save(foodtruckEntity);
    }

    @Override
    public FoodtruckEntity findFoodTruckByEmail(String email) {
        return foodTruckRepo.findByEmail(email);
    }

    @Override
    public FoodtruckEntity updateFoodTruck(FoodtruckEntity foodtruckEntity) {
        return foodTruckRepo.save(foodtruckEntity);
    }

    @Override
    public List<FoodtruckEntity> getAllFoodTrucksNearMe() {

        return foodTruckRepo.findAll();
    }

}
