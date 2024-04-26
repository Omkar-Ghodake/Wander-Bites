package com.foodtruck.foodtruck.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodtruckEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String foodTruckName;
    private String email;
    private String contact;
    private String address;
    private String password;
    private String role = "ROLE_FOODTRUCK";

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_foodtruck_id", referencedColumnName = "id")
    private List<GalleryPhotos> galleryPhotos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_foodtruck_id", referencedColumnName = "id")
    private List<FoodtruckFeedbacksEntity> feedbacks;

    private Double lat;
    private Double longi;
    private Double distance;
    private String openingTime;
    private String closingTime;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String foodTruckImage;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_foodtruck_id", referencedColumnName = "id")
    private List<MenuEntity> menuEntity;

    private String status;
    private Float rating;
}
