package ru.nova.novalib.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.nova.novalib.dao.UserRepository;
import ru.nova.novalib.domain.User;

@Configuration
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username -> {
            User user = userRepository.findByUsername(username);
            if(user!=null) return user;
            throw new UsernameNotFoundException("User " + username + " not found!");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/book/edit/**").access("hasRole('ADMIN')")
                .antMatchers("/", "/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .and()
                .build();
    }

}
