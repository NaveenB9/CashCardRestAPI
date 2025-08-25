package com.naveen.springboot.cashcardrestapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class CashCardSecurity {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(request -> request
            .requestMatchers("/cashcards/**")
            .hasRole("CARD-OWNER"))
        .httpBasic(Customizer.withDefaults())
        .csrf(csrf -> csrf.disable());
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
    User.UserBuilder users = User.builder();
    UserDetails bittu = users
        .username("bittu1")
        .password(passwordEncoder.encode("bat123"))
        .roles("CARD-OWNER")
        .build();

    UserDetails hankOwnsNoCards = users
        .username("hank-owns-no-cards")
        .password(passwordEncoder.encode("jkl123"))
        .roles("NON-OWNER")
        .build();

    UserDetails munna = users
        .username("munna2")
        .password(passwordEncoder.encode("nav579"))
        .roles("CARD-OWNER")
        .build();

    return new InMemoryUserDetailsManager(bittu, hankOwnsNoCards, munna);
  }
}
