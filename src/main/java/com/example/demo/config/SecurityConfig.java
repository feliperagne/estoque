package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends GlobalAuthenticationConfigurerAdapter{

   // @Autowired
//DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/funcionario/novo/**").hasAuthority("ADMIN")
                        .requestMatchers("/funcionario/prealterar/**").hasAuthority("ADMIN")
                        .requestMatchers("/funcionario/excluir/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                        


                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                        .failureUrl("/login-error")
                )
                .logout((logout) -> logout.invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login").permitAll())
                        
                        .exceptionHandling((exceptionHandling) -> exceptionHandling
                .accessDeniedPage("/acesso_negado")
                        
                        );
                        
        return http.build();
    }
}


