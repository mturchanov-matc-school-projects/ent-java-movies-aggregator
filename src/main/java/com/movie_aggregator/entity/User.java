package com.movie_aggregator.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


/**
 * @author mturchanov
 */
@Table(name = "users")
@Entity(name = "User")
public class User implements UserDetails {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private int enabled;

    @Column
    private String token;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "username")
    private List<Authority> authorityList;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "movies_user",
            joinColumns = @JoinColumn(name = "username"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "movie_id") //write how bridge table get connected with other target table/entity
    )
    private Set<Movie> myMovies;

    public User() {
    }

    public void addMovieToUser(Movie movie) {
        if(myMovies == null) {
            myMovies = new HashSet<>();
        }
        myMovies.add(movie);
    }

    public void addAuthorityToUser(Authority authority) {
        if(authorityList == null) {
            authorityList = new ArrayList<>();
        }
        authorityList.add(authority);
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled != 0;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
