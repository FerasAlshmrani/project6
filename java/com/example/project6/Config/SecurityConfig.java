package com.example.project6.Config;

import com.example.project6.Service.UserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final UserDetailService userDetailService;
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/register").permitAll()
                .requestMatchers("/api/v1/auth/get-users").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/update/{username}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/delete/{username}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/add-hotel-role/{username}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/add-user-role/{username}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/add").hasAuthority("USER")
                .requestMatchers("/api/v1/customer/update/{id}").hasAnyAuthority("USER","ADMIN")
                .requestMatchers("/api/v1/customer/delete/{username}").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/add-balance/{balance}").hasAuthority("USER")
                .requestMatchers("/api/v1/customer/book-hotel/{apartment}/{duration}").hasAuthority("USER")
                .requestMatchers("/api/v1/customer/duration-done/{username}/{apartment}").hasAnyAuthority("HOTEL","ADMIN")
                .requestMatchers("/api/v1/customer/get-only-booked").hasAnyAuthority("HOTEL","ADMIN")
                .requestMatchers("/api/v1/hotel/get-hotel").hasAnyAuthority("HOTEL","USER","ADMIN")
                .requestMatchers("/api/v1/hotel/add").hasAuthority("HOTEL")
                .requestMatchers("/api/v1/hotel/update/{id}").hasAuthority("HOTEL")
                .requestMatchers("/api/v1/hotel/delete/{id}").hasAuthority("HOTEL")
                .requestMatchers("/api/v1/hotel/search-hotel/{apartment}").hasAnyAuthority("USER","ADMIN","HOTEL")
                .requestMatchers("/api/v1/hotel/get-discount").hasAnyAuthority("HOTEL","ADMIN","USER")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }

}
