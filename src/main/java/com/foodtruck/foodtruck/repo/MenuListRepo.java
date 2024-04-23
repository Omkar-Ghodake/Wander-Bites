package com.foodtruck.foodtruck.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodtruck.foodtruck.entity.MenuEntity;

@Repository
public interface MenuListRepo extends JpaRepository<MenuEntity, Long> {
    public List<MenuEntity> findAllByCategory(String category);
}
