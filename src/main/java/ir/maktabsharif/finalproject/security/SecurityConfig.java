package ir.maktabsharif.finalproject.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasAnyRole("ADMINISTRATOR")
                        .requestMatchers("/customer/**").hasRole("CUSTOMER")
                        .requestMatchers("/specialist/**").hasRole("SPECIALIST")
                        .requestMatchers("/admin/approve-specialist/{specialistFirstName}/{specialistLastName}").hasRole("ADMINISTRATOR")
                        .requestMatchers("register-customer").permitAll()
                        .requestMatchers("register-specialist").permitAll()
                        .requestMatchers("/api/payments/pay-online", "/api/payments/captcha","/api/payments/payment-success").permitAll()
                        .anyRequest().authenticated())
                .anonymous(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


