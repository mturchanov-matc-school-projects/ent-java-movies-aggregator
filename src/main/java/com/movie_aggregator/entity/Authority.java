package com.movie_aggregator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
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

    public Authority() {
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
