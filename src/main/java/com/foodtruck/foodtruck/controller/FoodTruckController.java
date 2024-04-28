package com.foodtruck.foodtruck.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.foodtruck.foodtruck.config.CustomUserDetails;
import com.foodtruck.foodtruck.entity.FoodtruckEntity;
import com.foodtruck.foodtruck.entity.GalleryPhotos;
import com.foodtruck.foodtruck.entity.MenuEntity;
import com.foodtruck.foodtruck.entity.UserEntity;
import com.foodtruck.foodtruck.model.FoodTruckModel;
import com.foodtruck.foodtruck.model.MenuModel;
import com.foodtruck.foodtruck.service.FoodTruckService;
import com.foodtruck.foodtruck.service.GalleryPhotosService;
import com.foodtruck.foodtruck.service.MenuListServiceImpl;
import com.foodtruck.foodtruck.service.UserServiceImpl;

@Controller
@RequestMapping("/foodTruck")
public class FoodTruckController {

    @Autowired
    FoodTruckService foodTruckService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    GalleryPhotosService menuService;

    @Autowired
    MenuListServiceImpl menuListServiceImpl;

    @RequestMapping("/saveNewFoodTruck")
    public String saveNewFoodTruck(FoodTruckModel foodTruckModel, RedirectAttributes m,
            @RequestParam("img") MultipartFile img) throws IOException {
        try {
            UserEntity userEntit = userServiceImpl.findUser(foodTruckModel.getEmail());
            FoodtruckEntity foodtruck = foodTruckService.findFoodTruckByEmail(foodTruckModel.getEmail());
            if (userEntit == null && foodtruck == null) {
                if (foodTruckModel.getPassword().equals(foodTruckModel.getCpassword())
                        && foodTruckModel.getName().length() >= 3
                        && foodTruckModel.getFoodTruckName().length() >= 3 &&
                        foodTruckModel.getPassword().length() >= 8
                        && foodTruckModel.getEmail().length() > 9) {

                    FoodtruckEntity foodtruckEntity = new FoodtruckEntity();
                    foodtruckEntity.setName(foodTruckModel.getName());
                    foodtruckEntity.setFoodTruckName(foodTruckModel.getFoodTruckName());
                    foodtruckEntity.setEmail(foodTruckModel.getEmail());
                    foodtruckEntity.setLat(foodTruckModel.getLat());
                    foodtruckEntity.setLongi(foodTruckModel.getLongi());
                    foodtruckEntity.setPassword(passwordEncoder.encode(foodTruckModel.getPassword()));
                    if (img.isEmpty())
                        foodtruckEntity.setFoodTruckImage(null);
                    else
                        foodtruckEntity.setFoodTruckImage(Base64.getEncoder().encodeToString(img.getBytes()));

                    m.addFlashAttribute("error", "Registration Successful, LogIn");
                    foodTruckService.saveNewFoodTruck(foodtruckEntity);
                    return "redirect:/public/registerFoodTruck";
                }
                m.addFlashAttribute("error", "Please enter correct details");
                return "redirect:/public/registerFoodTruck";
            } else {
                m.addFlashAttribute("error", "Email already taken");
                return "redirect:/public/registerFoodTruck";

            }

        } catch (Exception e) {
            System.out.println("error : " + e);
            m.addAttribute("error", "Something Wrong Try Again");
            return "redirect:/public/registerFoodTruck";
        }

    }

    @RequestMapping("/foodTruckDashboard")
    public String foodTruckDashboard(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        FoodtruckEntity foodtruckEntity = foodTruckService.findFoodTruckByEmail(user.getUsername());
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
        for (MenuEntity menuItem : foodtruckEntity.getMenuEntity())
            categories.add(menuItem.getCategory());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> role = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        model.addAttribute("isUser", role.contains("ROLE_USER"));
        model.addAttribute("isFoodtruck", role.contains("ROLE_FOODTRUCK"));
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            model.addAttribute("isUserLogged", false);
        else
            model.addAttribute("isUserLogged", true);
        model.addAttribute("totalPhotos", foodtruckEntity.getGalleryPhotos().size());
        model.addAttribute("totalReviews", foodtruckEntity.getFeedbacks().size());
        model.addAttribute("categories", categories);
        model.addAttribute("foodtruck", foodtruckEntity);
        return "foodTruck";
    }

    @RequestMapping("/addGalleryPhoto")
    public String addMenuImg(@RequestParam("galleryPhoto") MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails customUserDetails, Model m)
            throws IOException {
        try {
            if (file.isEmpty())
                return "redirect:/foodTruck/foodTruckDashboard";
            GalleryPhotos menuEntity = new GalleryPhotos();
            menuEntity.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
            FoodtruckEntity foodtruckEntity = foodTruckService.findFoodTruckByEmail(customUserDetails.getUsername());
            foodtruckEntity.getGalleryPhotos().add(menuEntity);

            foodtruckEntity.setGalleryPhotos(foodtruckEntity.getGalleryPhotos());
            foodTruckService.updateFoodTruck(foodtruckEntity);
            return "redirect:/foodTruck/foodTruckDashboard";

        } catch (Exception e) {
            m.addAttribute("error", "Something Wrong Try Again");
            return "redirect:/foodTruck/foodTruckDashboard";
        }

    }

    @RequestMapping("/deleteGalleryPhoto/{id}")
    public String deleteMenu(@PathVariable("id") Long id, Model m) {
        try {
            menuService.deleteMenu(id);
            return "redirect:/foodTruck/foodTruckDashboard";
        } catch (Exception e) {
            m.addAttribute("error", "Something Wrong Try Again");
            return "redirect:/foodTruck/foodTruckDashboard";
        }
    }

    @RequestMapping("/getLocation")
    public String getLocation(@RequestParam("lat") String lat, @RequestParam("long") String longi,
            @AuthenticationPrincipal CustomUserDetails customUserDetails, Model m) {
        try {
            FoodtruckEntity foodtruckEntity = foodTruckService.findFoodTruckByEmail(customUserDetails.getUsername());
            foodtruckEntity.setLat(Double.parseDouble(lat));
            foodtruckEntity.setLongi(Double.parseDouble(longi));
            foodTruckService.updateFoodTruck(foodtruckEntity);
            return "redirect:/foodTruck/foodTruckDashboard";
        } catch (Exception e) {
            m.addAttribute("error", "Something Wrong Try Again");
            return "redirect:/foodTruck/foodTruckDashboard";
        }

    }

    @RequestMapping("/addMenu")
    public String addMenu() {
        return "/addMenuItem";
    }

    @RequestMapping("/addMenuItem")
    public String addMenu(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            Model model,
            MenuModel menuModel, @RequestParam("dishePhoto") MultipartFile file) {
        try {
            FoodtruckEntity foodtruckEntity = foodTruckService.findFoodTruckByEmail(customUserDetails.getUsername());

            MenuEntity menuEntity = new MenuEntity();

            menuEntity.setDisheName(menuModel.getDisheName());
            menuEntity.setDisheDescription(menuModel.getDisheDescription());
            menuEntity.setPrice(menuModel.getPrice());
            menuEntity.setDiscount(menuModel.getDiscount());
            menuEntity.setCategory(menuModel.getCategory());
            if (file.isEmpty())
                menuEntity.setDishePhoto(null);
            else
                menuEntity.setDishePhoto(Base64.getEncoder().encodeToString(file.getBytes()));

            foodtruckEntity.getMenuEntity().add(menuEntity);

            foodTruckService.updateFoodTruck(foodtruckEntity);

            return "redirect:/foodTruck/foodTruckDashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Something wrong try again.");
            return "redirect:/foodTruck/foodTruckDashboard";
        }

    }

    @RequestMapping("/deleteMenuItem/{id}")
    public String deleteMenuItem(@PathVariable("id") Long id, RedirectAttributes m) {

        if (menuListServiceImpl.deleteMenuItem(id)) {
            return "redirect:/foodTruck/foodTruckDashboard";
        } else
            return "redirect:/foodTruck/foodTruckDashboard";
    }

    @RequestMapping("/menuItem/{id}")
    public String menuItem(@PathVariable("id") Long id, Model model) {
        Optional<MenuEntity> menuItem = menuListServiceImpl.findMenuItem(id);
        if (menuItem.isPresent()) {
            model.addAttribute("menuItem", menuItem.get());
            return "/updateMenuItem";
        }
        return "redirect:/foodTruck/foodTruckDashboard";
    }

    @RequestMapping("/updateMenuItem")
    public String updateMenuItem(MenuModel menuModel, @RequestParam("dishePhoto") MultipartFile file)
            throws IOException {
        MenuEntity menuItem = new MenuEntity();
        Optional<MenuEntity> m = menuListServiceImpl.findMenuItem(Long.parseLong(menuModel.getId()));

        menuItem.setId(Long.parseLong(menuModel.getId()));
        menuItem.setDisheName(menuModel.getDisheName());
        menuItem.setDisheDescription(menuModel.getDisheDescription());
        menuItem.setPrice(menuModel.getPrice());
        menuItem.setDiscount(menuModel.getDiscount());
        menuItem.setCategory(menuModel.getCategory());
        if (!file.isEmpty())
            menuItem.setDishePhoto(Base64.getEncoder().encodeToString(file.getBytes()));
        else
            menuItem.setDishePhoto(m.get().getDishePhoto());

        menuListServiceImpl.updateMenuItem(menuItem);
        return "redirect:/foodTruck/foodTruckDashboard";
    }

    @RequestMapping("/truckProfile")
    public String updateTruck(Authentication authentication, Model model) {
        FoodtruckEntity foodtruckEntity = foodTruckService.findFoodTruckByEmail(authentication.getName());
        foodtruckEntity.setId(null);
        foodtruckEntity.setPassword(null);
        foodtruckEntity.setRole(null);
        model.addAttribute("foodtruck", foodtruckEntity);
        return "/updateFoodtruck";
    }

    @RequestMapping("/updateFoodTruckDetails")
    public String updateFoodTruckDetails(Authentication authentication, FoodTruckModel foodTruckModel,
            @RequestParam("file") MultipartFile file) throws IOException {
        FoodtruckEntity foodtruckEntity = foodTruckService.findFoodTruckByEmail(authentication.getName());
        foodtruckEntity.setOpeningTime(foodTruckModel.getOpeningTime());
        foodtruckEntity.setClosingTime(foodTruckModel.getClosingTime());
        foodtruckEntity.setName(foodTruckModel.getName());
        foodtruckEntity.setFoodTruckName(foodTruckModel.getFoodTruckName());
        foodtruckEntity.setContact(foodTruckModel.getContact());
        foodtruckEntity.setAddress(foodTruckModel.getAddress());
        if (!file.isEmpty())
            foodtruckEntity.setFoodTruckImage(Base64.getEncoder().encodeToString(file.getBytes()));
        foodTruckService.updateFoodTruck(foodtruckEntity);
        return "redirect:/foodTruck/foodTruckDashboard";
    }
}
