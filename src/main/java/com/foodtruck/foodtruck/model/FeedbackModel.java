package com.foodtruck.foodtruck.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackModel {
    private String feedback;
    private Integer rating;
    private String foodtruckEmail;
}
