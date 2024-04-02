package com.agira.shareDrive.appconfig;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
@NoArgsConstructor
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Override
//    public void init(HttpSecurity builder) throws Exception {
//
//    }
//
//    @Override
//    public void configure(HttpSecurity builder) throws Exception {
//        builder.csrf().disable()
//                .authorizeRequests()
//                .requestMatchers(HttpMethod.POST, "/v1/users/login", "/v1/users/register").permitAll()
//                .requestMatchers(HttpMethod.GET, "/v1/users").hasRole("ADMIN")
//                .requestMatchers("/v1/ride/**", "/v1/users/{id}", "/v1/vehicles/**", "/v1/ride/{id}").authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
//    }

//
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http){

    return http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/v1/users/login", "/v1/users/register").permitAll()
            .and()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.GET, "/v1/users").hasRole("ADMIN")
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/v1/ride/**", "/v1/users/{id}", "/v1/vehicles/**", "/v1/ride/{id}").authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/v1/users/login", "/v1/users/register").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("v1/ride/**", "v1/users/{id}").authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //.authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}



