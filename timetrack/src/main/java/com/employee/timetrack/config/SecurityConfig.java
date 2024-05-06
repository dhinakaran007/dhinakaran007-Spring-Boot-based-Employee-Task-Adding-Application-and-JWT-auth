//package com.employee.timetrack.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//                .antMatchers("/", "/home").permitAll() // Allow access to these URLs without authentication
//                .anyRequest().authenticated() // Require authentication for all other URLs
//                .and()
//            .formLogin() // Enable form-based authentication
//                .loginPage("/login") // Use a custom login page (optional)
//                .permitAll() // Allow access to the login page without authentication
//                .and()
//            .logout() // Enable logout support
//                .permitAll(); // Allow access to the logout URL without authentication
//    }
//
//    // Override this method to configure user details for authentication
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication() // Use in-memory authentication (for demo purposes)
//                .withUser("user").password("password").roles("USER"); // Define a user with username "user" and password "password"
//    }
//}
//
