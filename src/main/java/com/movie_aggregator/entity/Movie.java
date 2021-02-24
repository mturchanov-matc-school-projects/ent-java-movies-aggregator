package com.movie_aggregator.entity;

import javax.persistence.*;

/**
 * The type Movie.
 *
 * @author mturchanov
 */
@Table(name = "movies")
@Entity(name = "Movie")
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "kinopoisk_id")
    private String kinopoiskId;

    @Column(name = "description")
    private String description;

    @Column(name = "imdb_rating")
    private String imdbRating;

    @Column(name = "imdb_votes")
    private String imdbVotes;

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

    @Column(name = "eastern_name")
    private String easternName;

    @Column(name = "kinopoisk_rating")
    private String kinopoiskRating;

    @Column(name = "box_office")
    private String boxOffice;

    @Column
    private String duration;

    @Column
    private String genre;

    @Column
    private String director;

    @Column
    private String actors;

    @Column
    private String language;

    @Column
    private String country;

    @Column
    private String metascore;

    @Column
    private String image;

    @Column
    private String year;

    /**
     * Instantiates a new Movie.
     */
    public Movie() {
    }

    public Movie(String name, String kinopoiskId, String easternName, String kinopoiskRating, String year) {
        this.name = name;
        this.kinopoiskId = kinopoiskId;
        this.easternName = easternName;
        this.kinopoiskRating = kinopoiskRating;
        this.year = year;
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

    public Movie(String name, String imdbId, String description, String imdbRating,
                 String imdbVotes, String boxOffice, String duration, String genre,
                 String director, String actors, String language, String country,
                 String metascore, String image, String year) {
        this.name = name;
        this.imdbId = imdbId;
        this.description = description;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.boxOffice = boxOffice;
        this.duration = duration;
        this.genre = genre;
        this.director = director;
        this.actors = actors;
        this.language = language;
        this.country = country;
        this.metascore = metascore;
        this.image = image;
        this.year = year;
    }

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

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getKinopoiskId() {
        return kinopoiskId;
    }

    public void setKinopoiskId(String kinopoiskId) {
        this.kinopoiskId = kinopoiskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    public String getMetacriticRating() {
        return metacriticRating;
    }

    public void setMetacriticRating(String metacriticRating) {
        this.metacriticRating = metacriticRating;
    }

    public String getTheMovieDbRating() {
        return theMovieDbRating;
    }

    public void setTheMovieDbRating(String theMovieDbRating) {
        this.theMovieDbRating = theMovieDbRating;
    }

    public String getRottenTomatoesRating() {
        return rottenTomatoesRating;
    }

    public void setRottenTomatoesRating(String rottenTomatoesRating) {
        this.rottenTomatoesRating = rottenTomatoesRating;
    }

    public String gettV_comRating() {
        return tV_comRating;
    }

    public void settV_comRating(String tV_comRating) {
        this.tV_comRating = tV_comRating;
    }

    public String getFilmAffinityRating() {
        return filmAffinityRating;
    }

    public void setFilmAffinityRating(String filmAffinityRating) {
        this.filmAffinityRating = filmAffinityRating;
    }

    public String getEasternName() {
        return easternName;
    }

    public void setEasternName(String easternName) {
        this.easternName = easternName;
    }

    public String getKinopoiskRating() {
        return kinopoiskRating;
    }

    public void setKinopoiskRating(String kinopoiskRating) {
        this.kinopoiskRating = kinopoiskRating;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", kinopoiskId='" + kinopoiskId + '\'' +
                ", description='" + description + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", imdbVotes='" + imdbVotes + '\'' +
                ", metacriticRating='" + metacriticRating + '\'' +
                ", theMovieDbRating='" + theMovieDbRating + '\'' +
                ", rottenTomatoesRating='" + rottenTomatoesRating + '\'' +
                ", tV_comRating='" + tV_comRating + '\'' +
                ", filmAffinityRating='" + filmAffinityRating + '\'' +
                ", easternName='" + easternName + '\'' +
                ", kinopoiskRating='" + kinopoiskRating + '\'' +
                ", boxOffice='" + boxOffice + '\'' +
                ", duration='" + duration + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", metascore='" + metascore + '\'' +
                ", image='" + image + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}