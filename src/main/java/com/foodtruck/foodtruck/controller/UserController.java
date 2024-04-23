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
import org.springframework.web.bind.annotation.ModelAttribute;
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
                    && userModel.getEmail().length() >= 9) {
                UserEntity userEntity = new UserEntity();
                userEntity.setName(userModel.getName());
                userEntity.setEmail(userModel.getEmail());
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
        try {
            for (int i = 0; i < foodtruckEntity.size(); i++) {
                foodtruckEntity.get(i).setId(null);
                foodtruckEntity.get(i).setPassword(null);
                foodtruckEntity.get(i).setRole(null);
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
        Set<String> categories = new HashSet<String>();
        for (MenuEntity menuEntity : foodtruckEntity.getMenuEntity())
            categories.add(menuEntity.getCategory());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> role = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("isUser", role.contains("ROLE_USER"));
        model.addAttribute("isFoodtruck", role.contains("ROLE_FOODTRUCK"));
        model.addAttribute("categories", categories);
        model.addAttribute("foodtruck", foodtruckEntity);
        return "/foodtruckDetails";
    }

    @RequestMapping("/menuItemFilter/category={category}&email={email}")
    public String menuItemFilter(@PathVariable("category") String category, @PathVariable("email") String email,
            Model model) {
        FoodtruckEntity foodtruckEntity = foodTruckServiceImpl.findFoodTruckByEmail(email);
        Set<String> categories = new HashSet<String>();
        for (MenuEntity menuEntity : foodtruckEntity.getMenuEntity())
            categories.add(menuEntity.getCategory());

        List<MenuEntity> menuList = menuListServiceImpl.filterMenuList(category);

        foodtruckEntity.setMenuEntity(menuList);
        foodtruckEntity.setId(null);
        foodtruckEntity.setRole(null);
        foodtruckEntity.setPassword(null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> role = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("isUser", role.contains("ROLE_USER"));
        model.addAttribute("isFoodtruck", role.contains("ROLE_FOODTRUCK"));
        model.addAttribute("categories", categories);
        model.addAttribute("foodtruck", foodtruckEntity);

        return "/foodtruckDetails";
    }
}
