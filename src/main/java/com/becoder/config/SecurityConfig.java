package com.becoder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService getDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
		http.csrf().disable()
		.authorizeHttpRequests().requestMatchers("/","/register","/signin","/saveUser","/forgot-password","/reset-password","/images/**","/css/**","/videos/**","/membership","/uploads","/profile","/update","/delete/**","/deleteUser/**","/password/forgot/**","/password/reset/**","/bmi_calculator/**","/nutrition_form/**","/nutrition_result/**","/search/**","/workouts/**","/workouts/add/**","/workouts/edit/{id}/**","/welcome/**","/workouth/**","/home1/**","/calorie/**","/bmr/**","/recipe/**","/yoga","/Explore","/index2/**","/index3/**","/pushup/**","/kneepushup/**","/inclinepush/**","/burpees/**","/russiantwist/**","/legraises/**","/plank","/bicyclecrunches","/squats/**","/jumpingjack1/**","/pushup1/**","/templates/**","/inclimepush1","/js/**","/admin/**").permitAll()
		.requestMatchers("/**").authenticated().and()
		.formLogin().loginPage("/signin").loginProcessingUrl("/userLogin")
		//.usernameParameter("email")
		.defaultSuccessUrl("/",true).permitAll();
		
		
		return http.build();
	}

	}
