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
                                                      .requestMatchers("/login")
                                                      .permitAll()
                                                      .requestMatchers("/usuario/**")
                                                      .hasAnyRole("ADMIN", "USER", "STAFF")
                                                      .anyRequest()
                                                      .authenticated())
                                                      .formLogin(form -> form
                                                              .loginPage("/login")
                                                              .defaultSuccessUrl("/usuario", true)
                                                              .failureUrl("/login?error=true"))
                                                      .logout(logout -> logout
                                                              .logoutUrl("/logout")
                                                              .logoutSuccessUrl("/login?logout=true")
                                                              .invalidateHttpSession(true)
                                                              .deleteCookies("JSESSIONID")
                                                              .permitAll());
        return http.build();
    }
    
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager()
    {
        UserDetails user = User.builder().username("user").password("{noop}password").roles("USER").build();
        UserDetails userA = User.builder().username("admin").password("{noop}password").roles("ADMIN").build();
        UserDetails userB = User.builder().username("staff").password("{noop}password").roles("STAFF").build();
        
        return new InMemoryUserDetailsManager(user, userA, userB);
    }
}