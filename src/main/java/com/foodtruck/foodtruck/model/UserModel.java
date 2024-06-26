package com.foodtruck.foodtruck.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String name;
    private String email;
    private String contact;
    private String password;
    private String cpassword;
}
