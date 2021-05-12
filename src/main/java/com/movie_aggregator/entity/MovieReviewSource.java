package com.movie_aggregator.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * This class represents movie review source
 * bridge table entity
 *
 * @author mturchanov
 */

@Entity(name = "MovieReviewSource")
@Table(name = "movie_review_source")
public class MovieReviewSource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @ManyToOne
    @JoinColumn(name = "review_source_name",
            foreignKey = @ForeignKey(name = "movie_review_source_ibfk_3"))
    private ReviewsSourcesLookup reviewSource;

    @ManyToOne
    @JoinColumn(name = "movie_id",
            foreignKey = @ForeignKey(name = "movie_review_source_ibfk_1"))
    private Movie movie;

    public MovieReviewSource(ReviewsSourcesLookup reviewSource, Movie movie, String url) {
        this.reviewSource = reviewSource;
        this.movie = movie;
        this.url = url;
    }

    @Column
    private String url;

    public MovieReviewSource() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ReviewsSourcesLookup getReviewSource() {
        return reviewSource;
    }

    public void setReviewSource(ReviewsSourcesLookup reviewSource) {
        this.reviewSource = reviewSource;
    }

    public Movie getMovie() {
        return movie;
    }


    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MovieReviewSource{" +
                "id=" + id +
                ", reviewSource=" + reviewSource +
                ", url='" + url + '\'' +
                '}';
    }
}
