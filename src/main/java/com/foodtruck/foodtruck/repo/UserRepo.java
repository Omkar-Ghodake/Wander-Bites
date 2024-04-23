package com.foodtruck.foodtruck.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodtruck.foodtruck.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    public UserEntity findUserByEmail(String email);
}
