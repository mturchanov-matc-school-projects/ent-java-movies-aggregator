package com.movie_aggregator.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import javax.sql.DataSource;

/**
 * @author mturchanov
 */

@EnableWebSecurity //check this class that is responsible for security configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

    }


//    /** authorization - giving permissions access **/
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authorizeRequests()
//                .antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")  // main page('/') can see emp, hr, managers
//                .antMatchers("/hr_info").hasRole("HR") // /hr_info for hr
//                .antMatchers("/manager_info/**").hasRole("MANAGER") // MANAGER has access to all links that begin with '/,manager_info/
//                .and().formLogin().permitAll(); // ask for login/password for all urls
//    }
}
