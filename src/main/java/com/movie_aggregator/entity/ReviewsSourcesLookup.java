package com.movie_aggregator.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents review source
 * lookup
 *
 * @author mturchanov
 */
@Table(name = "reviews_sources_lookup")
@Entity(name = "ReviewsSourcesLookup")
public class ReviewsSourcesLookup {

    @Column
    @Id
    private String name;

    @Column
    private String url;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String icon;

    @Column
    private String description;

    @Column
    private String feature;


    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "user_review_source_lookup",
            joinColumns = @JoinColumn(name = "review_source_name"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "username") //write how bridge table get connected with other target table/entity
    )
    private Set<User> users;

    /**
     * Transient variable that is used
     * for view to mark user's chosen reviews
     */
    @Transient
    private boolean checked;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<MovieReviewSource> movieReviewSources = new HashSet<>();

    public ReviewsSourcesLookup() {
    }

    public Set<MovieReviewSource> getMovieReviewSources() {
        return movieReviewSources;
    }

    public void setMovieReviewSources(Set<MovieReviewSource> movieReviewSources) {
        this.movieReviewSources = movieReviewSources;
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

    public boolean isChecked() {
        return checked;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewsSourcesLookup lookup = (ReviewsSourcesLookup) o;
        return Objects.equals(name, lookup.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ReviewsSourcesLookup{" +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", checked=" + checked +
                '}';
    }


}
