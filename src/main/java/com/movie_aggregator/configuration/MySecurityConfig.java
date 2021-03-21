package com.movie_aggregator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import javax.sql.DataSource;

/**
 * @author mturchanov
 */

@EnableWebSecurity //check this class that is responsible for security configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        // authentication manager
        auth.jdbcAuthentication().dataSource(dataSource);

    }



//    /** authorization - giving permissions access **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http builder configurations for authorize requests and form login
        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")  // main page('/') can see emp, hr, managers
                .antMatchers("/api/**").hasRole("admin")
                .and()
                .formLogin()
                //.loginPage("/login.html")
                //.loginProcessingUrl("/login")
                .defaultSuccessUrl("/test", true)
                .failureUrl("/login.html?error=true")
                .permitAll(); // ask for login/password for all urls
    }
}
