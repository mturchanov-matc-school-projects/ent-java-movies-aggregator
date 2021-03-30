package com.movie_aggregator.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Authority.
 *
 * @author mturchanov
 */
@Table(name = "authorities")
@Entity(name = "Authority")
public class Authority {
   // @Id
    //private int id;

    @Column
    @Id
    private String username;

    @Column
    private String authority = "ROLE_USER";

    /**
     * Instantiates a new Authority.
     */
    public Authority() {
    }


    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets authority.
     *
     * @return the authority
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * Sets authority.
     *
     * @param authority the authority
     */
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
