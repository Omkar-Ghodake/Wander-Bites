package com.foodtruck.foodtruck.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.foodtruck.foodtruck.entity.FoodtruckEntity;
import com.foodtruck.foodtruck.entity.UserEntity;
import com.foodtruck.foodtruck.service.FoodTruckServiceImpl;
import com.foodtruck.foodtruck.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LogoutHandleImpl implements LogoutHandler {
    CustomUserDetails customUserDetails;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    FoodTruckServiceImpl foodTruckServiceImpl;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Set<String> role = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (role.contains("ROLE_USER")) {
            UserEntity userEntity = userServiceImpl.findUser(authentication.getName());
            userEntity.setLat(null);
            userEntity.setLongi(null);
            userServiceImpl.updateUser(userEntity);

            System.out.println(userEntity);
        } else {
            FoodtruckEntity foodtruckEntity = foodTruckServiceImpl.findFoodTruckByEmail(authentication.getName());
            foodtruckEntity.setStatus("Closed");
            foodTruckServiceImpl.updateFoodTruck(foodtruckEntity);
        }
    }
}
