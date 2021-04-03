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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.sql.DataSource;

/**
 * @author mturchanov
 */

@EnableWebSecurity //check this class that is responsible for security configuration
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
               // .antMatchers("/api/**").hasRole("ADMIN")
                .antMatchers("/resultUserList/**").hasRole("USER")
                .antMatchers("/test").hasRole("USER")
                .and()
                .formLogin()
                //.loginPage("/login.html")
                //.loginProcessingUrl("/login")
                //.defaultSuccessUrl("/", true)
                .and()
               // .failureUrl("/login.html?error=true")
                .logout().logoutSuccessUrl("/").permitAll()
                .permitAll(); // ask for login/password for all urls


        //http.authorizeRequests()
        //        .antMatchers("/api/**").access("hasRole('ROLE_ADMIN')")
        //        .anyRequest().permitAll()
        //        .and()
        //        .formLogin().loginPage("/login.jsp")
        //        .usernameParameter("username").passwordParameter("password")
        //        .and()
        //       // .logout().logoutSuccessUrl("/login?logout")
        //       // .and()
        //       // .exceptionHandling().accessDeniedPage("/403")
        //       // .and()
        //        .csrf();
    }
}
