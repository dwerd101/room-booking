/*
package ru.metrovagonmash.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.metrovagonmash.config.security.auth.UserService;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userDetailsService;

    // FIXME: 21.07.2021 Это не окончательный вариант. Нужно будет его доработать.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

      */
/*  http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login/**").anonymous()
                .antMatchers("/enable/**").authenticated()
                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSISIONID","remember-me")
                .logoutSuccessUrl("/login");*//*

        */
/* http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login/**").anonymous()
                .antMatchers("/department/**","/employees/**","/report/**").hasAuthority("admin:read")
                .antMatchers("/reports/**").hasAnyAuthority("admin:read","user:read")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .and()
                .rememberMe()
                .and()
                .logout()
                .logoutUrl("/logout")
                //*.logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))*//*
*/
/*
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSISIONID","remember-me")
                .logoutSuccessUrl("/login");

*//*


    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y,12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
*/
