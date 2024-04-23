package com.foodtruck.foodtruck.model;

import lombok.Data;

@Data
public class MenuModel {
    private String id;
    private String disheName;
    private String disheDescription;
    private Long price;
    private Long discount;
    private String category;
}
