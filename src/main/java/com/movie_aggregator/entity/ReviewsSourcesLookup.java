package com.movie_aggregator.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author mturchanov
 */
@Table(name = "reviews_sources_lookup")
@Entity(name = "ReviewsSourcesLookup")
public class ReviewsSourcesLookup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String name;

    @Column
    private String url;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "user_review_source_lookup",
            joinColumns = @JoinColumn(name = "review_source_id"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "username") //write how bridge table get connected with other target table/entity
    )
    private Set<User> users;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<MovieReviewSource> movieReviewSources = new HashSet<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
