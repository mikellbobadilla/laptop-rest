package com.sting.ordenadores.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public UserDetailsService userDetailsService(){
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(
            User.builder()
                    .username("user")
                    .password(encoder().encode("password"))
                    .roles("user")
                    .build()
    );

    manager.createUser(
            User.builder()
                    .username("admin")
                    .password(encoder().encode("adminpass"))
                    .roles("ADMIN", "USER")
                    .build()
    );
    return manager;
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.authorizeHttpRequests(
            authorize -> authorize.anyRequest().authenticated()
    ).formLogin(withDefaults()).httpBasic(withDefaults());

    return http.build();
  }




  @Bean
  public PasswordEncoder encoder(){
    return new BCryptPasswordEncoder();
  }

}
