package com.movie_aggregator.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
 * The type Movie.
 *
 * @author mturchanov
 */
@Table(name = "movies")
@Entity(name = "Movie")
public class Movie {

    @Id
    private int id;

    @Column
    private String name;
    //@ManyToMany(cascade = CascadeType.ALL)
    @ManyToMany(cascade = {CascadeType.PERSIST,  CascadeType.REFRESH, CascadeType.DETACH}
            , fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_search",
            joinColumns = @JoinColumn(name = "movie_id"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "search_id") //write how bridge table get connected with other target table/entity
    )
    private Set<Search> searches;

    @OneToMany(targetEntity=Image.class, mappedBy="movie",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Image> images;



    //@OneToOne(targetEntity=ReviewSource.class, mappedBy="movie",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    //private ReviewSource reviewSources;
    @OneToOne(mappedBy = "movie", cascade = {CascadeType.PERSIST,  CascadeType.REFRESH, CascadeType.MERGE})
    private ReviewSource reviewSources;


    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "movies_user",
            joinColumns = @JoinColumn(name = "movie_id"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "username") //write how bridge table get connected with other target table/entity
    )
    private Set<User> users;


    //@OneToMany(targetEntity=ReviewsSources.class, mappedBy="movie",cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    //private Set<ReviewsSources> reviewsSources;



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

    @Column(name = "kinopoisk_votes")
    private String kinopoiskVotes;

    @Column
    private String awards;
    @Column
    private String writer;
    @Column
    private String released;
    @Column
    private String production;
    @Column(name="audience_rating")
    private String audienceRating;

    /**
     * has such format: "%s(%s)"
     * at the left is total review number,
     * in parenthesis is number of good reviews
     **/
    @Column(name = "kinopoisk_reviews")
    private String kinopoiskReviews;

    //@Column
    //private String images;

    @Transient
    private boolean isAddedToUserList;

    /**
     * Instantiates a new Movie.
     */
    public Movie() {
    }

    /**
     * Instantiates a new Movie.
     *
     * @param name  the name
     * @param image the image
     * @param year  the year
     */
    public Movie(String name, String image, String year) {
        this.name = name;
        this.image = image;
        this.year = year;
    }

    /**
     * Instantiates a new Movie.
     *
     * @param id    the id
     * @param name  the name
     * @param image the image
     * @param year  the year
     */
    public Movie(int id, String name, String image, String year) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.year = year;
    }

    /**
     * Instantiates a new Movie.
     *
     * @param name            the name
     * @param kinopoiskId     the kinopoisk id
     * @param easternName     the eastern name
     * @param kinopoiskRating the kinopoisk rating
     * @param year            the year
     */
    public Movie(String name, String kinopoiskId, String easternName,
                 String kinopoiskRating, String year) {
        this.name = name;
        this.kinopoiskId = kinopoiskId;
        this.easternName = easternName;
        this.kinopoiskRating = kinopoiskRating;
        this.year = year;
    }

    /**
     * Instantiates a new Movie.
     *
     * @param name        the name
     * @param imdbId      the imdb id
     * @param description the description
     * @param imdbRating  the imdb rating
     * @param imdbVotes   the imdb votes
     * @param boxOffice   the box office
     * @param duration    the duration
     * @param genre       the genre
     * @param director    the director
     * @param actors      the actors
     * @param language    the language
     * @param country     the country
     * @param metascore   the metascore
     * @param image       the image
     * @param year        the year
     */
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

    /**
     * Instantiates a new Movie.
     *
     * @param nameEn      the name en
     * @param nameRu      the name ru
     * @param imdbId      the imdb id
     * @param filmId      the film id
     * @param shortDesc   the short desc
     * @param duration    the duration
     * @param year        the year
     * @param kVotes      the k votes
     * @param rating      the rating
     * @param image       the image
     * @param description the description
     */
    public Movie(String nameEn, String nameRu, String imdbId, String filmId,
                 String shortDesc, String duration, String year, String kVotes,
                 String rating, String image, String description) {
        this.name = nameEn;
        this.imdbId = imdbId;
        this.kinopoiskId = filmId;
        this.director = shortDesc;
        this.description = description;
        this.easternName = nameRu;
        this.kinopoiskRating = rating;
        this.duration = duration;
        this.image = image;
        this.year = year;
        this.kinopoiskVotes = kVotes;
    }


    public boolean isAddedToUserList() {
        return isAddedToUserList;
    }

    public void setAddedToUserList(boolean addedToUserList) {
        isAddedToUserList = addedToUserList;
    }

    /**
     * Gets kinopoisk votes.
     *
     * @return the kinopoisk votes
     */
    public String getKinopoiskVotes() {
        return kinopoiskVotes;
    }


    /**
     * Sets kinopoisk votes.
     *
     * @param kinopoiskVotes the kinopoisk votes
     */
    public void setKinopoiskVotes(String kinopoiskVotes) {
        this.kinopoiskVotes = kinopoiskVotes;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
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
     * Gets kinopoisk id.
     *
     * @return the kinopoisk id
     */
    public String getKinopoiskId() {
        return kinopoiskId;
    }

    /**
     * Sets kinopoisk id.
     *
     * @param kinopoiskId the kinopoisk id
     */
    public void setKinopoiskId(String kinopoiskId) {
        this.kinopoiskId = kinopoiskId;
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
     * Gets imdb votes.
     *
     * @return the imdb votes
     */
    public String getImdbVotes() {
        return imdbVotes;
    }

    /**
     * Sets imdb votes.
     *
     * @param imdbVotes the imdb votes
     */
    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    /**
     * Gets metacritic rating.
     *
     * @return the metacritic rating
     */
    public String getMetacriticRating() {
        return metacriticRating;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getAudienceRating() {
        return audienceRating;
    }

    public void setAudienceRating(String audienceRating) {
        this.audienceRating = audienceRating;
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
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
     * Gets eastern name.
     *
     * @return the eastern name
     */
    public String getEasternName() {
        return easternName;
    }

    /**
     * Sets eastern name.
     *
     * @param easternName the eastern name
     */
    public void setEasternName(String easternName) {
        this.easternName = easternName;
    }

    /**
     * Gets kinopoisk rating.
     *
     * @return the kinopoisk rating
     */
    public String getKinopoiskRating() {
        return kinopoiskRating;
    }

    /**
     * Sets kinopoisk rating.
     *
     * @param kinopoiskRating the kinopoisk rating
     */
    public void setKinopoiskRating(String kinopoiskRating) {
        this.kinopoiskRating = kinopoiskRating;
    }

    /**
     * Gets box office.
     *
     * @return the box office
     */
    public String getBoxOffice() {
        return boxOffice;
    }

    /**
     * Sets box office.
     *
     * @param boxOffice the box office
     */
    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets duration.
     *
     * @param duration the duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Gets genre.
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets genre.
     *
     * @param genre the genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets director.
     *
     * @return the director
     */
    public String getDirector() {
        return director;
    }

    /**
     * Sets director.
     *
     * @param director the director
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Gets actors.
     *
     * @return the actors
     */
    public String getActors() {
        return actors;
    }

    /**
     * Sets actors.
     *
     * @param actors the actors
     */
    public void setActors(String actors) {
        this.actors = actors;
    }

    /**
     * Gets language.
     *
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets language.
     *
     * @param language the language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets metascore.
     *
     * @return the metascore
     */
    public String getMetascore() {
        return metascore;
    }

    /**
     * Sets metascore.
     *
     * @param metascore the metascore
     */
    public void setMetascore(String metascore) {
        this.metascore = metascore;
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

    /**
     * Gets searches.
     *
     * @return the searches
     */
    public Set<Search> getSearches() {
        return searches;
    }

    /**
     * Sets searches.
     *
     * @param searches the searches
     */
    public void setSearches(Set<Search> searches) {
        this.searches = searches;
    }

    /**
     * Add search to movie.
     *
     * @param search the search
     */
    public void addSearchToMovie(Search search) {
        if(searches == null) {
            searches = new HashSet<>();
        }
        searches.add(search);
    }


    public ReviewSource getReviewSources() {
        return reviewSources;
    }

    public void setReviewSources(ReviewSource reviewSources) {
        this.reviewSources = reviewSources;
    }

    public void addImageToMovie(Image image) {
        if(images == null) {
            images = new HashSet<>();
        }
        images.add(image);
    }

    public String getKinopoiskReviews() {
        return kinopoiskReviews;
    }

    public void setKinopoiskReviews(String kinopoiskReviews) {
        this.kinopoiskReviews = kinopoiskReviews;
    }



    //TODO: fix equals for test.repositary.GenericDaoTest
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && Objects.equals(name, movie.name)
                && Objects.equals(searches, movie.searches)
                && Objects.equals(imdbId, movie.imdbId)
                && Objects.equals(kinopoiskId, movie.kinopoiskId)
                && Objects.equals(description, movie.description)
                && Objects.equals(imdbRating, movie.imdbRating)
                && Objects.equals(imdbVotes, movie.imdbVotes)
                && Objects.equals(metacriticRating, movie.metacriticRating)
                && Objects.equals(theMovieDbRating, movie.theMovieDbRating)
                && Objects.equals(rottenTomatoesRating, movie.rottenTomatoesRating)
                && Objects.equals(tV_comRating, movie.tV_comRating)
                && Objects.equals(filmAffinityRating, movie.filmAffinityRating)
                && Objects.equals(easternName, movie.easternName)
                && Objects.equals(kinopoiskRating, movie.kinopoiskRating)
                && Objects.equals(boxOffice, movie.boxOffice)
                && Objects.equals(duration, movie.duration)
                && Objects.equals(genre, movie.genre)
                && Objects.equals(director, movie.director)
                && Objects.equals(actors, movie.actors)
                && Objects.equals(language, movie.language)
                && Objects.equals(country, movie.country)
                && Objects.equals(metascore, movie.metascore)
                && Objects.equals(image, movie.image)
                && Objects.equals(year, movie.year)
                && Objects.equals(kinopoiskVotes, movie.kinopoiskVotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, searches, imdbId, kinopoiskId, description,
                imdbRating, imdbVotes, metacriticRating, theMovieDbRating,
                rottenTomatoesRating, tV_comRating, filmAffinityRating, easternName,
                kinopoiskRating, boxOffice, duration, genre, director, actors, language,
                country, metascore, image, year, kinopoiskVotes);
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
                ", isAdded='" + isAddedToUserList + '\'' +
                '}';
    }
}