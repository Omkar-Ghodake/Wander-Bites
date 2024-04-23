package com.foodtruck.foodtruck.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodtruck.foodtruck.entity.MenuEntity;
import com.foodtruck.foodtruck.repo.MenuListRepo;

@Service
public class MenuListServiceImpl implements MenuListService {

    @Autowired
    MenuListRepo menuListRepo;

    @Override
    public List<MenuEntity> filterMenuList(String category) {
        return menuListRepo.findAllByCategory(category);
    }

    @Override
    public boolean deleteMenuItem(Long id) {
        menuListRepo.deleteById(id);
        Optional<MenuEntity> menuEntity = menuListRepo.findById(id);
        return menuEntity.isEmpty();

    }

    @Override
    public MenuEntity updateMenuItem(MenuEntity menuEntity) {
        return menuListRepo.save(menuEntity);
    }

    @Override
    public Optional<MenuEntity> findMenuItem(Long id) {
        return menuListRepo.findById(id);
    }

}
