package com.foodtruck.foodtruck.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodtruck.foodtruck.entity.GalleryPhotos;

@Repository
public interface GalleryPhotosRepo extends JpaRepository<GalleryPhotos, Long> {

}
