package com.emeka.delivery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.emeka.delivery.Security.JwtAuthEntryPoint;
import com.emeka.delivery.Security.JwtAuthenticationFilter;
import com.emeka.delivery.Services.imple.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorization -> authorization
                        .requestMatchers("/delivery/v1/auth/login").permitAll()
                        .requestMatchers("/delivery/v1/auth/logued").permitAll()
                        .requestMatchers("/delivery/v1/auth/logout").permitAll()
                        .requestMatchers("/delivery/v1/direcciones/paises").permitAll()
                        .requestMatchers("/delivery/v1/direcciones/departamentos/*").permitAll()
                        .requestMatchers("/delivery/v1/direcciones/municipios/*").permitAll()
                        .requestMatchers("/delivery/v1/clientes/obtenerDireccionCliente").permitAll()

                        .requestMatchers("/delivery/v1/empresas/obtenerEmpresas/*").permitAll()
                        .requestMatchers("/delivery/v1/clientes/crearDireccionCliente").permitAll()
                        .requestMatchers("/delivery/v1/productos/*").permitAll()
                        .requestMatchers("/delivery/v1/empresas/crearDireccionEmpresa").hasAnyAuthority("VENDEDOR")
                        
                        .requestMatchers("/delivery/v1/carritos/guardarCarrito").permitAll()
                        .requestMatchers("/delivery/v1/carritos/verCarrito").permitAll()
                        .requestMatchers("/delivery/v1/empresas/guardarEmpresa").hasAnyAuthority("VENDEDOR")
                        .requestMatchers("/delivery/v1/empresas/guardarEmpresa").hasAnyAuthority("ADMINISTRADOR")
                        
                        

                                
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint))
                .headers(AbstractHttpConfigurer::disable)
                .build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    @Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
            registry.addMapping("/**")
                   .allowedOrigins("http://localhost:8000") // ⚠️ Cambia según tu frontend                
                 .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowCredentials(true); // 🔥 Permitir el uso de cookies
        }
    };
}
}
