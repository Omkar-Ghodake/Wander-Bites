package com.foodtruck.foodtruck.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.foodtruck.foodtruck.entity.FoodtruckEntity;
import com.foodtruck.foodtruck.entity.UserEntity;

public class CustomUserDetails implements UserDetails {
    FoodtruckEntity foodtruckEntity;
    UserEntity userEntity;

    public CustomUserDetails(FoodtruckEntity foodtruckEntity) {
        this.foodtruckEntity = foodtruckEntity;
    }

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (foodtruckEntity != null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(foodtruckEntity.getRole());
            return Arrays.asList(authority);
        } else {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole());
            return Arrays.asList(authority);
        }

    }

    @Override
    public String getPassword() {
        if (foodtruckEntity != null)
            return foodtruckEntity.getPassword();
        else
            return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        if (foodtruckEntity != null)
            return foodtruckEntity.getEmail();
        else
            return userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
