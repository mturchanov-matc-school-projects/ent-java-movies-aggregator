package enity;

import javax.persistence.*;

/**
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
    private double imdbRating;

    @Column(name = "metacritic_rating")
    private int metacriticRating;

    @Column(name = "movie_db_rating")
    private double theMovieDbRating;

    @Column(name = "rotten_tomatoes_rating")
    private int rottenTomatoesRating;

    @Column(name = "tv_com_rating")
    private double tV_comRating;

    @Column(name = "film_affinity_rating")
    private double filmAffinityRating;

    @Column
    private String image;

    @Column(name = "year")
    private String year;

    public Movie() {
    }

    public Movie(String name, String imdbId, double imdbRating,
                 int metacriticRating, double theMovieDbRating, int rottenTomatoesRating, double tV_comRating,
                 double filmAffinityRating, String image, String year) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getMetacriticRating() {
        return metacriticRating;
    }

    public void setMetacriticRating(int metacriticRating) {
        this.metacriticRating = metacriticRating;
    }

    public double getTheMovieDbRating() {
        return theMovieDbRating;
    }

    public void setTheMovieDbRating(double theMovieDbRating) {
        this.theMovieDbRating = theMovieDbRating;
    }

    public int getRottenTomatoesRating() {
        return rottenTomatoesRating;
    }

    public void setRottenTomatoesRating(int rottenTomatoesRating) {
        this.rottenTomatoesRating = rottenTomatoesRating;
    }

    public double gettV_comRating() {
        return tV_comRating;
    }

    public void settV_comRating(double tV_comRating) {
        this.tV_comRating = tV_comRating;
    }

    public double getFilmAffinityRating() {
        return filmAffinityRating;
    }

    public void setFilmAffinityRating(double filmAffinityRating) {
        this.filmAffinityRating = filmAffinityRating;
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
}