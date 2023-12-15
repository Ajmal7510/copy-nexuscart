package com.ecommerceproject1.ecommerce.Config.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfigeration {

    @Autowired
    private ScucessHandler scucessHandler;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                aouth->aouth
                        .requestMatchers("/**","/login","signup","toOtp").permitAll()
                        .requestMatchers("admin/**").hasAuthority("ADMIN")
                        .requestMatchers("user/**").hasAnyAuthority("USER","ADMIN")

                        .anyRequest().authenticated()
                         )

                         .formLogin(form->
                                 form
                                         .loginPage("/login").permitAll()
                                         .successHandler(scucessHandler)
                                         .failureHandler((request, response, exception) -> {
                                                     request.getSession().setAttribute("loginError","Invalid UserName or Password");
                                                     response.sendRedirect("/login");
                                                 }

                                                 )

                         ).sessionManagement(
                        httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer
                                        .maximumSessions(1)
                                        .maxSessionsPreventsLogin(true)
                                        .expiredUrl("/login?sessionExpired")
                )  .rememberMe(rememberMe->rememberMe
                        .rememberMeParameter("remember-me")
                        .tokenValiditySeconds(604800))
                .logout(
                        log->log
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                );

                return http.build();
    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return  new BCryptPasswordEncoder();

    }


}
