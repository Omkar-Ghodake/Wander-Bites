package com.foodtruck.foodtruck.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String disheName;
    private String disheDescription;
    private Long price;
    private Long discount;
    private String category;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String dishePhoto = null;

}
