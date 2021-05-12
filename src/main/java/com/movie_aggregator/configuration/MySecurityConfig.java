package com.movie_aggregator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import javax.sql.DataSource;

/**
 * MySecurityConfig where authorization is
 * are adjusted
 */

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

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
                .antMatchers("/").hasAnyRole("ADMIN", "USER")  // main page('/') can see emp, hr, managers
                .antMatchers("/resultUserList/**").hasRole("USER")
                .antMatchers("/test").hasRole("USER")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .permitAll(); // ask for login/password for all urls

    }

}
