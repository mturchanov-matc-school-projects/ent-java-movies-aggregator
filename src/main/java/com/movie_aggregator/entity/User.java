package com.movie_aggregator.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;


/**
 * The type User.
 *
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

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "movies_user",
            joinColumns = @JoinColumn(name = "username"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "movie_id") //write how bridge table get connected with other target table/entity
    )
    private Set<Movie> myMovies;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_review_source_lookup",
            joinColumns = @JoinColumn(name = "username"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "review_source_id") //write how bridge table get connected with other target table/entity
    )
    private Set<ReviewsSourcesLookup> reviewsSourceLookups;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Add movie to user.
     *
     * @param movie the movie
     */
    public void addMovieToUser(Movie movie) {
        if(myMovies == null) {
            myMovies = new HashSet<>();
        }
        myMovies.add(movie);
    }

    /**
     * Add authority to user.
     *
     * @param authority the authority
     */
    public void addAuthorityToUser(Authority authority) {
        if(authorityList == null) {
            authorityList = new ArrayList<>();
        }
        authorityList.add(authority);
    }
    public void addReviewSourceToUser(ReviewsSourcesLookup reviewsSource) {
        if(reviewsSourceLookups == null) {
            reviewsSourceLookups = new HashSet<>();
        }
        reviewsSourceLookups.add(reviewsSource);
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

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }


    public Set<ReviewsSourcesLookup> getReviewsSources() {
        return reviewsSourceLookups;
    }

    public void setReviewsSources(Set<ReviewsSourcesLookup> reviewsSourceLookups) {
        this.reviewsSourceLookups = reviewsSourceLookups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets enabled.
     *
     * @return the enabled
     */
    public int getEnabled() {
        return enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
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

    public void removeMovieFromUser(Movie movie) {
        if (myMovies != null) {
            myMovies.remove(movie);
        }
    }
}
