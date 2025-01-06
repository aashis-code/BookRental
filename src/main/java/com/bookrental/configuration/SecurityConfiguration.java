package com.bookrental.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bookrental.security.JwtAuthenticationFilter;
import com.bookrental.security.MemberDetailsService;

@Configuration
//@EnableWebSecurity
@EnableMethodSecurity( prePostEnabled = true)
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final MemberDetailsService memberDetailsService;
	
	private static final String[] SWAGGER_URLS = {"/api/v1/auth/**","/v3/**","/v3/api-docs.yaml","/swagger-ui.html","/swagger-ui/**"};

	public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, MemberDetailsService memberDetailsService) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.memberDetailsService = memberDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(SWAGGER_URLS).permitAll()
//                .requestMatchers("/api/member/**").hasAuthority("ROLE_LIBRARIAN")
                .anyRequest().permitAll());
//        http.exceptionHandling( ex-> new UnAuthorizedException("You are unauthorized to access."));
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);//						.anyRequest().permitAll())
        return http.build();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(memberDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
