package com.foodtruck.foodtruck.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodtruck.foodtruck.entity.GalleryPhotos;
import com.foodtruck.foodtruck.repo.GalleryPhotosRepo;

@Service
public class GalleryPhotosServiceImpl implements GalleryPhotosService {

    @Autowired
    GalleryPhotosRepo menuRepo;

    @Override
    public GalleryPhotos addMenu(GalleryPhotos menuEntity) {
        return menuRepo.save(menuEntity);
    }

    @Override
    public Boolean deleteMenu(Long id) {
        menuRepo.deleteById(id);
        Optional<GalleryPhotos> menuEntity = menuRepo.findById(id);

        return menuEntity.isEmpty();

    }

}
