package com.foodtruck.foodtruck.calculations;

import java.util.List;

import org.springframework.stereotype.Component;

import com.foodtruck.foodtruck.entity.FoodtruckEntity;

@Component
public class SortFoodTrucksByDistance {

    public List<FoodtruckEntity> sortObject(List<FoodtruckEntity> foodtruckEntities) {
        FoodtruckEntity f = new FoodtruckEntity();
        for (int i = 0; i < foodtruckEntities.size() - 1; i++) {
            for (int j = 1; j < foodtruckEntities.size(); j++) {

                if (foodtruckEntities.get(i).getDistance() > foodtruckEntities.get(j).getDistance()) {

                    // Set Value for Temporary variable
                    f.setName(foodtruckEntities.get(i).getName());
                    f.setEmail(foodtruckEntities.get(i).getEmail());
                    f.setFoodTruckName(foodtruckEntities.get(i).getFoodTruckName());
                    f.setLat(foodtruckEntities.get(i).getLat());
                    f.setLongi(foodtruckEntities.get(i).getLongi());
                    f.setDistance(foodtruckEntities.get(i).getDistance());
                    f.setGalleryPhotos(foodtruckEntities.get(i).getGalleryPhotos());

                    // Set value for i index
                    foodtruckEntities.get(i).setName(foodtruckEntities.get(j).getName());
                    foodtruckEntities.get(i).setEmail(foodtruckEntities.get(j).getEmail());
                    foodtruckEntities.get(i).setFoodTruckName(foodtruckEntities.get(j).getFoodTruckName());
                    foodtruckEntities.get(i).setGalleryPhotos(foodtruckEntities.get(j).getGalleryPhotos());
                    foodtruckEntities.get(i).setLat(foodtruckEntities.get(j).getLat());
                    foodtruckEntities.get(i).setLongi(foodtruckEntities.get(j).getLongi());
                    foodtruckEntities.get(i).setDistance(foodtruckEntities.get(j).getDistance());

                    // Set value for j index
                    foodtruckEntities.get(j).setName(f.getName());
                    foodtruckEntities.get(j).setEmail(f.getEmail());
                    foodtruckEntities.get(j).setFoodTruckName(f.getFoodTruckName());
                    foodtruckEntities.get(j).setGalleryPhotos(f.getGalleryPhotos());
                    foodtruckEntities.get(j).setLat(f.getLat());
                    foodtruckEntities.get(j).setLongi(f.getLongi());
                    foodtruckEntities.get(j).setDistance(f.getDistance());
                }
            }
        }

        return foodtruckEntities;
    }

}
