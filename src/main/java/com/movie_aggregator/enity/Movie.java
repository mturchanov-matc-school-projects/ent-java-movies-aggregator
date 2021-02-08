package com.movie_aggregator.enity;

import javax.persistence.*;

/**
 * The type Movie.
 *
 * @author mturchanov
 */
@Entity(name = "Movie")
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "description")
    private String description;

    @Column(name = "imdb_rating")
    private String imdbRating;

    @Column(name = "metacritic_rating")
    private String metacriticRating;

    @Column(name = "movie_db_rating")
    private String theMovieDbRating;

    @Column(name = "rotten_tomatoes_rating")
    private String rottenTomatoesRating;

    @Column(name = "tv_com_rating")
    private String tV_comRating;

    @Column(name = "film_affinity_rating")
    private String filmAffinityRating;

    @Column
    private String image;

    @Column(name = "year")
    private String year;

    /**
     * Instantiates a new Movie.
     */
    public Movie() {
    }

    /**
     * Instantiates a new Movie.
     *
     * @param name                 the name
     * @param imdbId               the imdb id
     * @param imdbRating           the imdb rating
     * @param metacriticRating     the metacritic rating
     * @param theMovieDbRating     the the movie db rating
     * @param rottenTomatoesRating the rotten tomatoes rating
     * @param tV_comRating         the t v com rating
     * @param filmAffinityRating   the film affinity rating
     * @param image                the image
     * @param year                 the year
     */
    public Movie(String name, String imdbId, String imdbRating,
                 String metacriticRating, String theMovieDbRating, String rottenTomatoesRating, String tV_comRating,
                 String filmAffinityRating, String image, String year) {
        this.name = name;
        this.imdbId = imdbId;
        this.imdbRating = imdbRating;
        this.metacriticRating = metacriticRating;
        this.theMovieDbRating = theMovieDbRating;
        this.rottenTomatoesRating = rottenTomatoesRating;
        this.tV_comRating = tV_comRating;
        this.filmAffinityRating = filmAffinityRating;
        this.image = image;
        this.year = year;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets imdb id.
     *
     * @return the imdb id
     */
    public String getImdbId() {
        return imdbId;
    }

    /**
     * Sets imdb id.
     *
     * @param imdbId the imdb id
     */
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets imdb rating.
     *
     * @return the imdb rating
     */
    public String getImdbRating() {
        return imdbRating;
    }

    /**
     * Sets imdb rating.
     *
     * @param imdbRating the imdb rating
     */
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    /**
     * Gets metacritic rating.
     *
     * @return the metacritic rating
     */
    public String getMetacriticRating() {
        return metacriticRating;
    }

    /**
     * Sets metacritic rating.
     *
     * @param metacriticRating the metacritic rating
     */
    public void setMetacriticRating(String metacriticRating) {
        this.metacriticRating = metacriticRating;
    }

    /**
     * Gets the movie db rating.
     *
     * @return the the movie db rating
     */
    public String getTheMovieDbRating() {
        return theMovieDbRating;
    }

    /**
     * Sets the movie db rating.
     *
     * @param theMovieDbRating the the movie db rating
     */
    public void setTheMovieDbRating(String theMovieDbRating) {
        this.theMovieDbRating = theMovieDbRating;
    }

    /**
     * Gets rotten tomatoes rating.
     *
     * @return the rotten tomatoes rating
     */
    public String getRottenTomatoesRating() {
        return rottenTomatoesRating;
    }

    /**
     * Sets rotten tomatoes rating.
     *
     * @param rottenTomatoesRating the rotten tomatoes rating
     */
    public void setRottenTomatoesRating(String rottenTomatoesRating) {
        this.rottenTomatoesRating = rottenTomatoesRating;
    }

    /**
     * Gets v com rating.
     *
     * @return the v com rating
     */
    public String gettV_comRating() {
        return tV_comRating;
    }

    /**
     * Sets v com rating.
     *
     * @param tV_comRating the t v com rating
     */
    public void settV_comRating(String tV_comRating) {
        this.tV_comRating = tV_comRating;
    }

    /**
     * Gets film affinity rating.
     *
     * @return the film affinity rating
     */
    public String getFilmAffinityRating() {
        return filmAffinityRating;
    }

    /**
     * Sets film affinity rating.
     *
     * @param filmAffinityRating the film affinity rating
     */
    public void setFilmAffinityRating(String filmAffinityRating) {
        this.filmAffinityRating = filmAffinityRating;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets year.
     *
     * @param year the year
     */
    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", description='" + description + '\'' +
                ", imdbRating=" + imdbRating +
                ", metacriticRating=" + metacriticRating +
                ", theMovieDbRating=" + theMovieDbRating +
                ", rottenTomatoesRating=" + rottenTomatoesRating +
                ", tV_comRating=" + tV_comRating +
                ", filmAffinityRating=" + filmAffinityRating +
                ", image='" + image + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}