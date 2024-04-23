package com.foodtruck.foodtruck.service;

import com.foodtruck.foodtruck.entity.UserEntity;

public interface UserService {
    public UserEntity saveNewUser(UserEntity userEntity);

    public UserEntity findUser(String email);

    public UserEntity updateUser(UserEntity userEntity);
}
