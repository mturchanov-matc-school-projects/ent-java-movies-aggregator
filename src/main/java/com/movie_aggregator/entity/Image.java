package com.movie_aggregator.entity;

import javax.persistence.*;


/**
 * This class represents image
 *
 * @author mturchanov
 */

@Table(name = "images")
@Entity(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(unique = true)
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    private Movie movie;


    public Image() {
    }

    public Image(String url) {
        this.url = url;
    }

    public Image(Movie movie, String url) {
        this.movie = movie;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
