package com.foodtruck.foodtruck.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodTruckModel {
    private String name;
    private String foodTruckName;
    private String email;
    private String address;
    private String password;
    private String cpassword;
}
