package com.digis01.IHernandezProgramacionNCapas.Configuration;

import com.digis01.IHernandezProgramacionNCapas.Controller.ErrorController;
import com.digis01.IHernandezProgramacionNCapas.DAO.UserDetailsJPAService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig 
{
    private final UserDetailsJPAService userDetailsJPAService;
    
    public SpringSecurityConfig(UserDetailsJPAService userDetailsJPAService)
    {
        this.userDetailsJPAService = userDetailsJPAService;
    }
            
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ErrorController failureHandler) throws Exception
    {
        http.authorizeHttpRequests(configurer -> configurer
                                                      .requestMatchers("/login", "/css/**", "/js/**", "/images/**")
                                                      .permitAll()
                                                      .requestMatchers("/usuario/cargamasiva/**")
                                                      .hasRole("Administrador")
                                                      .requestMatchers("/usuario/status/**")
                                                      .permitAll()
                                                      .requestMatchers("/usuario/**")
                                                      .hasAnyRole("Administrador", "Editor", "Lector", "Invitado")
                                                      .anyRequest().authenticated())
                                                      .formLogin(form -> form
                                                              .loginPage("/login")
                                                              .failureHandler(failureHandler)
                                                              .defaultSuccessUrl("/usuario", true)
                                                              .failureUrl("/login?error=disabled")
                                                              .failureUrl("/login?error=bad_credentials"))
                                                      .logout(logout -> logout
                                                              .logoutUrl("/logout")
                                                              .logoutSuccessUrl("/login?logout=true")
                                                              .invalidateHttpSession(true)
                                                              .deleteCookies("JSESSIONID")
                                                              .permitAll())
                                                       .cors()
                                                       .and()
                                                       .csrf()
                                                       .disable()
                                                       .userDetailsService(userDetailsJPAService)
                                                      .exceptionHandling(exception -> exception
                                                                .accessDeniedPage("/error/403"));
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }
    
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager()
//    {
//        UserDetails userA = User.builder().username("admin").password("{noop}password").roles("ADMIN").build();
//        UserDetails userS = User.builder().username("staff").password("{noop}password").roles("STAFF").build();
//        UserDetails user = User.builder().username("user").password("{noop}password").roles("USER").build();
//        UserDetails userG = User.builder().username("guest").password("{noop}password").roles("GUEST").build();
//
//        return new InMemoryUserDetailsManager(userA, userS, user, userG);
//    }
}