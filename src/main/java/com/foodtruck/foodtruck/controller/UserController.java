package com.foodtruck.foodtruck.controller;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.foodtruck.foodtruck.calculations.FindDistance;
import com.foodtruck.foodtruck.calculations.SortFoodTrucksByDistance;
import com.foodtruck.foodtruck.config.CustomUserDetails;
import com.foodtruck.foodtruck.entity.FoodtruckEntity;
import com.foodtruck.foodtruck.entity.FoodtruckFeedbacksEntity;
import com.foodtruck.foodtruck.entity.MenuEntity;
import com.foodtruck.foodtruck.entity.UserEntity;
import com.foodtruck.foodtruck.model.FeedbackModel;
import com.foodtruck.foodtruck.model.UserModel;
import com.foodtruck.foodtruck.service.FoodTruckServiceImpl;
import com.foodtruck.foodtruck.service.MenuListServiceImpl;
import com.foodtruck.foodtruck.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    SortFoodTrucksByDistance sortFoodTrucksByDistance;

    @Autowired
    FindDistance findDistance;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    FoodTruckServiceImpl foodTruckServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MenuListServiceImpl menuListServiceImpl;

    @RequestMapping("/saveNewUser")
    public String saveNewUser(UserModel userModel, RedirectAttributes m) {
        UserEntity u = userServiceImpl.findUser(userModel.getEmail());
        FoodtruckEntity f = foodTruckServiceImpl.findFoodTruckByEmail(userModel.getEmail());
        if (u == null && f == null) {
            if (userModel.getPassword().equals(userModel.getCpassword())
                    && userModel.getName().length() >= 3
                    && userModel.getEmail().length() >= 9
                    && userModel.getContact().length() == 10) {
                UserEntity userEntity = new UserEntity();
                userEntity.setName(userModel.getName());
                userEntity.setEmail(userModel.getEmail());
                userEntity.setContact(userModel.getContact());
                userEntity.setPassword(passwordEncoder.encode(userModel.getPassword()));
                userServiceImpl.saveNewUser(userEntity);
                m.addFlashAttribute("error", "Registration Successful, LogIn");

                return "redirect:/public/registerUser";
            } else {
                m.addFlashAttribute("error", "Please Enter Correct details");
            }
        } else {
            m.addFlashAttribute("error", "Email id already taken");
        }

        return "redirect:/public/registerUser";
    }

    @RequestMapping("/userDashboard")
    public String userDashboard(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        UserEntity u = userServiceImpl.findUser(user.getUsername());
        List<FoodtruckEntity> foodtruckEntity = foodTruckServiceImpl.getAllFoodTrucksNearMe();
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        DecimalFormat df = new DecimalFormat("0.0");
        try {
            float rating = 0;
            for (int i = 0; i < foodtruckEntity.size(); i++) {
                foodtruckEntity.get(i).setId(null);
                foodtruckEntity.get(i).setPassword(null);
                foodtruckEntity.get(i).setRole(null);
                if (foodtruckEntity.get(i).getFeedbacks().size() != 0) {
                    for (int j = 0; j < foodtruckEntity.get(i).getFeedbacks().size(); j++) {
                        rating += foodtruckEntity.get(i).getFeedbacks().get(j).getRating();
                    }
                    rating = rating / Float.valueOf(foodtruckEntity.get(i).getFeedbacks().size());
                } else {
                    foodtruckEntity.get(i).setRating(0f);
                }

                foodtruckEntity.get(i).setRating(Float.valueOf(df.format(rating)));
                rating = 0;
                foodtruckEntity.get(i)
                        .setDistance(Double.parseDouble(
                                decimalFormat.format(findDistance.calculateDistance(u.getLat(), u.getLongi(),
                                        foodtruckEntity.get(i).getLat(), foodtruckEntity.get(i).getLongi()))));
            }
            foodtruckEntity = sortFoodTrucksByDistance.sortObject(foodtruckEntity);
        } catch (Exception e) {
            for (int i = 0; i < foodtruckEntity.size(); i++) {
                foodtruckEntity.get(i).setId(null);
                foodtruckEntity.get(i).setPassword(null);
                foodtruckEntity.get(i).setRole(null);
            }
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> role = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("isUser", role.contains("ROLE_USER"));
        model.addAttribute("isFoodtruck", role.contains("ROLE_FOODTRUCK"));
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            model.addAttribute("isUserLogged", false);
        else
            model.addAttribute("isUserLogged", true);

        model.addAttribute("user", u);
        model.addAttribute("foodtrucks", foodtruckEntity);
        return "users";
    }

    @RequestMapping("/getUserLocation")
    public String getUserLocation(@RequestParam("lat") String lat, @RequestParam("long") String longi,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        UserEntity userEntity = userServiceImpl.findUser(customUserDetails.getUsername());
        userEntity.setLat(Double.parseDouble(lat));
        userEntity.setLongi(Double.parseDouble(longi));
        userServiceImpl.updateUser(userEntity);
        return "redirect:/user/userDashboard";
    }

    @RequestMapping("/saveFeedback")
    public String saveFeedback(FeedbackModel feedbackModel, Model m,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        try {
            if (feedbackModel.getFeedback() != null) {
                UserEntity userEntity = userServiceImpl.findUser(customUserDetails.getUsername());
                FoodtruckEntity foodtruckEntity = foodTruckServiceImpl
                        .findFoodTruckByEmail(feedbackModel.getFoodtruckEmail());

                FoodtruckFeedbacksEntity foodtruckFeedbacks = new FoodtruckFeedbacksEntity();
                foodtruckFeedbacks.setFeedback(feedbackModel.getFeedback());
                foodtruckFeedbacks.setRating(feedbackModel.getRating());
                foodtruckFeedbacks.setUserName(userEntity.getName());

                foodtruckEntity.getFeedbacks().add(foodtruckFeedbacks);
                foodTruckServiceImpl.updateFoodTruck(foodtruckEntity);

                return "redirect:/user/foodtruckDetails/" + feedbackModel.getFoodtruckEmail();
            } else {
                return "redirect:/user/foodtruckDetails/" + feedbackModel.getFoodtruckEmail();
            }
        } catch (Exception e) {
            m.addAttribute("error", "Something Wrong");
            return "redirect:/user/foodtruckDetails/" + feedbackModel.getFoodtruckEmail();
        }

    }

    @RequestMapping("/foodtruckDetails/{email}")
    public String showFoodTruckDetails(@PathVariable("email") String email, Model model) {
        FoodtruckEntity foodtruckEntity = foodTruckServiceImpl.findFoodTruckByEmail(email);
        foodtruckEntity.setId(null);
        foodtruckEntity.setPassword(null);
        foodtruckEntity.setRole(null);
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        float rating = 0;
        if (foodtruckEntity.getFeedbacks().size() != 0) {
            for (int i = 0; i < foodtruckEntity.getFeedbacks().size(); i++) {
                rating += foodtruckEntity.getFeedbacks().get(i).getRating();
            }
            rating = rating / foodtruckEntity.getFeedbacks().size();
            foodtruckEntity.setRating(Float.valueOf(decimalFormat.format(rating)));
        } else {
            foodtruckEntity.setRating(0f);
        }

        Set<String> categories = new HashSet<String>();
        for (MenuEntity menuEntity : foodtruckEntity.getMenuEntity())
            categories.add(menuEntity.getCategory());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> role = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("isUser", role.contains("ROLE_USER"));
        model.addAttribute("isFoodtruck", role.contains("ROLE_FOODTRUCK"));
        model.addAttribute("categories", categories);
        model.addAttribute("foodtruck", foodtruckEntity);
        model.addAttribute("totalReviews", foodtruckEntity.getFeedbacks().size());
        model.addAttribute("totalPhotos", foodtruckEntity.getGalleryPhotos().size());
        return "/foodtruckDetails";
    }

    @RequestMapping("/userProfile")
    public String userProfile(Authentication authentication, Model model) {
        UserEntity userEntity = userServiceImpl.findUser(authentication.getName());
        userEntity.setId(null);
        userEntity.setPassword(null);
        userEntity.setRole(null);
        model.addAttribute("user", userEntity);
        return "updateUser";
    }

    @RequestMapping("/updateUserProfile")
    public String updateUserProfile(Authentication authentication, UserModel userModel) {
        UserEntity userEntity = userServiceImpl.findUser(authentication.getName());
        if (userModel.getContact().length() == 10 && userModel.getName().length() >= 3) {
            userEntity.setContact(userModel.getContact());
            userEntity.setName(userModel.getName());
            userServiceImpl.updateUser(userEntity);
        }
        return "redirect:/user/userDashboard";
    }
}
