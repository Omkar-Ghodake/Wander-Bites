package com.foodtruck.foodtruck.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.foodtruck.foodtruck.entity.FoodtruckEntity;
import com.foodtruck.foodtruck.service.FoodTruckServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    FoodTruckServiceImpl foodTruckServiceImpl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Set<String> role = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (role.contains("ROLE_FOODTRUCK")) {
            FoodtruckEntity foodtruck = foodTruckServiceImpl.findFoodTruckByEmail(authentication.getName());
            foodtruck.setStatus("Open");
            foodTruckServiceImpl.updateFoodTruck(foodtruck);
            response.sendRedirect("/foodTruck/foodTruckDashboard");
        } else
            response.sendRedirect("/user/userDashboard");
    }

}
