package com.foodtruck.foodtruck.service;

import java.util.List;
import java.util.Optional;

import com.foodtruck.foodtruck.entity.MenuEntity;

public interface MenuListService {
    public List<MenuEntity> filterMenuList(String category);

    public boolean deleteMenuItem(Long id);

    public MenuEntity updateMenuItem(MenuEntity menuEntity);

    public Optional<MenuEntity> findMenuItem(Long id);
}
