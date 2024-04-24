package com.foodtruck.foodtruck.calculations;

import java.util.List;

import org.springframework.stereotype.Component;

import com.foodtruck.foodtruck.entity.FoodtruckEntity;

@Component
public class SortFoodTrucksByDistance {

    public List<FoodtruckEntity> sortObject(List<FoodtruckEntity> foodtruckEntities) {
        FoodtruckEntity f = new FoodtruckEntity();
        for (int i = 0; i < foodtruckEntities.size() - 1; i++) {
            for (int j = i + 1; j < foodtruckEntities.size(); j++) {
                if (foodtruckEntities.get(i).getDistance() > foodtruckEntities.get(j).getDistance()) {
                    f = foodtruckEntities.get(i);
                    foodtruckEntities.set(i, foodtruckEntities.get(j));
                    foodtruckEntities.set(j, f);
                }
            }
        }
        return foodtruckEntities;
    }

}
