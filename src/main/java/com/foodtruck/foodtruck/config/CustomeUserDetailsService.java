package com.foodtruck.foodtruck.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.foodtruck.foodtruck.entity.FoodtruckEntity;
import com.foodtruck.foodtruck.entity.UserEntity;
import com.foodtruck.foodtruck.repo.UserRepo;
import com.foodtruck.foodtruck.service.FoodTruckServiceImpl;

@Component
public class CustomeUserDetailsService implements UserDetailsService {

    @Autowired
    FoodTruckServiceImpl foodTruckServiceImpl;

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        FoodtruckEntity foodtruckEntity = foodTruckServiceImpl.findFoodTruckByEmail(email);
        UserEntity userEntity = userRepo.findUserByEmail(email);
        if (foodtruckEntity == null && userEntity == null)
            throw new UsernameNotFoundException("User Not Found");
        else {
            if (foodtruckEntity == null)
                return new CustomUserDetails(userEntity);
            else
                return new CustomUserDetails(foodtruckEntity);
        }

    }

}
