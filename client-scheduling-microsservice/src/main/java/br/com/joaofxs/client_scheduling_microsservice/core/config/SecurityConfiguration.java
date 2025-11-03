package br.com.joaofxs.client_scheduling_microsservice.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configuração para os endpoints da API que exigem segurança
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF, comum em APIs stateless
                .securityMatcher("/api/**") // Aplica esta cadeia de filtros APENAS para caminhos que começam com /api/
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Endpoints de autenticação são públicos
                        .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // Apenas ADMIN acessa /api/admin/**
                        .requestMatchers("/api/user/**").hasAnyAuthority("ADMIN", "USER") // ADMIN e USER acessam /api/user/**
                        .anyRequest().authenticated() // Todas as outras requisições exigem autenticação
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sessão stateless
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona nosso filtro JWT

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
//        // Configuração para endpoints públicos (Swagger, etc.) que não precisam de segurança
//        http.securityMatcher("/swagger-ui/**", "/v3/api-docs/**").authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
//        return http.build();
//    }
}
