package com.foodtruck.foodtruck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodtruck.foodtruck.entity.UserEntity;
import com.foodtruck.foodtruck.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserEntity saveNewUser(UserEntity userEntity) {
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity findUser(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return userRepo.save(userEntity);
    }

}
