package com.foodtruck.foodtruck.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    LogoutHandleImpl logoutHandleImpl;

    @Autowired
    CustomAuthSuccessHandler customAuthSuccessHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomeUserDetailsService();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/public/**", "/foodTruck/saveNewFoodTruck", "/user/saveNewUser",
                                        "/img/**", "/js/**")
                                .permitAll()
                                .requestMatchers("/foodTruck/**").hasRole("FOODTRUCK")
                                .requestMatchers("/user/**").hasRole("USER")
                                .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/public/loginPage")
                        .successHandler(customAuthSuccessHandler)
                        .loginProcessingUrl("/login").permitAll())
                .logout(logout -> logout.addLogoutHandler(logoutHandleImpl));
        return http.build();
    }
}
