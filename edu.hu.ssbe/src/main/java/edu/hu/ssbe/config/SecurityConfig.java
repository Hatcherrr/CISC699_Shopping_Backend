package edu.hu.ssbe.config;

import edu.hu.ssbe.handlers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandlerImpl;

    @Autowired
    private AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;

    @Autowired
    private AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandlerImpl;

    @Autowired
    private UserDetailsService userDetailsService;

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                    .and()
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/index.html", "/ds/*").permitAll()
//                    .antMatchers("/users").hasAnyAuthority("ADMIN", "MARKETER")
                    .anyRequest().authenticated()
                    .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointImpl)
                    .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerImpl)
                    .and()
                .formLogin()
                    .permitAll()
                    .loginProcessingUrl("/login")
                    .successHandler(authenticationSuccessHandlerImpl)
                    .failureHandler(authenticationFailureHandlerImpl)
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .and()
                .logout()
                    .permitAll()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(logoutSuccessHandlerImpl)
                    .and()
                .httpBasic();
    }

    @Bean		// singleton, put the return object of this function into the container
    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder(11);
        return new PasswordEncoderHandler();
    }

    @Autowired  // autowire the parameter
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"/*"http://localhost:4200"*/); // You should only set trusted site here. e.g. http://localhost:4200 means only this site can access.
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","HEAD","OPTIONS"));
        configuration.addAllowedHeader("*"/*MSI_NAME*/);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
