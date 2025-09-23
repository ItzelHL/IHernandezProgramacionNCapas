package com.digis01.IHernandezProgramacionNCapas.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig 
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(configurer -> configurer
                                                      .requestMatchers("/login", "/css/**", "/js/**", "/images/**")
                                                      .permitAll()
                                                      .requestMatchers("/usuario/cargamasiva/**")
                                                      .hasRole("ADMIN")
                                                      .requestMatchers("/usuario/**")
                                                      .hasAnyRole("ADMIN", "STAFF", "USER", "GUEST")
                                                      .anyRequest().authenticated())
                                                      .formLogin(form -> form
                                                              .loginPage("/login")
                                                              .defaultSuccessUrl("/usuario", true)
                                                              .failureUrl("/login?error=true"))
                                                      .logout(logout -> logout
                                                              .logoutUrl("/logout")
                                                              .logoutSuccessUrl("/login?logout=true")
                                                              .invalidateHttpSession(true)
                                                              .deleteCookies("JSESSIONID")
                                                              .permitAll())
                                                      .exceptionHandling(exception -> exception
                                                                .accessDeniedPage("/error/403"));
        return http.build();
    }
    
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager()
    {
        UserDetails userA = User.builder().username("admin").password("{noop}password").roles("ADMIN").build();
        UserDetails userS = User.builder().username("staff").password("{noop}password").roles("STAFF").build();
        UserDetails user = User.builder().username("user").password("{noop}password").roles("USER").build();
        UserDetails userG = User.builder().username("guest").password("{noop}password").roles("GUEST").build();
        
        
        
        return new InMemoryUserDetailsManager(userA, userS, user, userG);
    }
}