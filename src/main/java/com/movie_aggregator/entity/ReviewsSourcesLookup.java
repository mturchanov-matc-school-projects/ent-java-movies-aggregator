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

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id")
    //private int id;

    @Column
    @Id
    private String name;

    @Column
    private String url;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "user_review_source_lookup",
            joinColumns = @JoinColumn(name = "review_source_name"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "username") //write how bridge table get connected with other target table/entity
    )
    private Set<User> users;
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
