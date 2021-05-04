package com.movie_aggregator.entity;

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

    /**
     * generating id manually based on en_title+year.
     * it might be useful because app has 2 film versions(kinopoisk/imdb) -> easier to retrieve
     */
    @Id
    private int id;


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

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<MovieReviewSource> movieReviewSources;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "movies_user",
            joinColumns = @JoinColumn(name = "movie_id"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "username") //write how bridge table get connected with other target table/entity
    )
    private Set<User> users;



    @Column(name = "eng_name")
    private String engName;

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "kinopoisk_id")
    private String kinopoiskId;

    @Column(name = "imdb_description")
    private String imdbDescription;

    @Column(name = "kinopoisk_description")
    private String kinopoiskDescription;

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

    @Column(name = "rus_name")
    private String rusName;

    @Column(name = "kinopoisk_rating")
    private String kinopoiskRating;

    @Column(name = "box_office")
    private String boxOffice;

    @Column
    private String duration;

    @Column(name = "imdb_genre")
    private String imdbGenre;

    @Column(name = "kinopoisk_genre")
    private String kinopoiskGenre;

    @Column(name = "imdb_director")
    private String imdbDirector;

    @Column(name = "kinopoisk_director")
    private String kinopoiskDirector;

    @Column
    private String actors;

    @Column
    private String language;

    @Column(name = "imdb_country")
    private String imdbCountry;

    @Column(name = "kinopoisk_country")
    private String kinopoiskCountry;

    @Column
    private String metascore;

    @Column(name = "kinopoisk_poster")
    private String kinopoiskImage;

    @Column(name = "imdb_poster")
    private String imdbPoster;

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

    @Column(name="imdb_distributor")
    private String imdbDistributor;
    @Column(name="kinopoisk_distributor")
    private String kinopoiskDistributor;

    /**
     * has such format: "%s(%s)"
     * at the left is total review number,
     * in parenthesis is number of good reviews
     **/
    @Column(name = "kinopoisk_reviews")
    private String kinopoiskReviews;


    /**
     * Transient variable that is
     * in view for logged users (displaying add btn or not)
     */
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
     * @param engName  the name
     * @param kinopoiskImage the image
     * @param year  the year
     */
    public Movie(String engName, String kinopoiskImage, String year) {
        this.engName = engName;
        this.kinopoiskImage = kinopoiskImage;
        this.year = year;
    }

    //filmId, nameEn, nameRu, year, director, duration, rating, image, kVotes, countries.toString(), genres.toString())

    public Movie(int id, String engName, String rusName, String kinopoiskRating, String duration,
                 String kinopoiskGenre, String kinopoiskDirector, String kinopoiskCountry,
                 String kinopoiskImage, String year, String kinopoiskVotes, String kinopoiskId) {
        this.id = id;
        this.engName = engName;
        this.rusName = rusName;
        this.kinopoiskRating = kinopoiskRating;
        this.duration = duration;
        this.kinopoiskGenre = kinopoiskGenre;
        this.kinopoiskDirector = kinopoiskDirector;
        this.kinopoiskCountry = kinopoiskCountry;
        this.kinopoiskImage = kinopoiskImage;
        this.year = year;
        this.kinopoiskVotes = kinopoiskVotes;
        this.kinopoiskId = kinopoiskId;
    }

    public Movie(String imdbId, String kinopoiskDescription, String imdbRating, String imdbVotes, String boxOffice, String audienceRating, String kinopoiskReviews) {
        this.imdbId = imdbId;
        this.kinopoiskDescription = kinopoiskDescription;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.boxOffice = boxOffice;
        this.audienceRating = audienceRating;
        this.kinopoiskReviews = kinopoiskReviews;
    }

    /**
     * Instantiates a new Movie.
     *
     * @param id    the id
     * @param engName  the name
     * @param image the image
     * @param year  the year
     */
    public Movie(int id, String engName, String image, String year, String imdbId) {
        this.id = id;
        this.engName = engName;
        this.imdbPoster = image;
        this.year = year;
        this.imdbId = imdbId;
    }

    /**
     * Instantiates a new Movie.
     *
     * @param engName            the name
     * @param kinopoiskId     the kinopoisk id
     * @param rusName     the eastern name
     * @param kinopoiskRating the kinopoisk rating
     * @param year            the year
     */
    public Movie(String engName, String kinopoiskId, String rusName,
                 String kinopoiskRating, String year) {
        this.engName = engName;
        this.kinopoiskId = kinopoiskId;
        this.rusName = rusName;
        this.kinopoiskRating = kinopoiskRating;
        this.year = year;
    }



    public Movie(String imdbDescription, String imdbRating, String imdbVotes, String metacriticRating,
                 String rottenTomatoesRating, String boxOffice, String duration, String imdbGenre, String imdbDirector,
                 String actors, String language, String imdbCountry, String metascore, String awards, String writer,
                 String released, String production, String audienceRating) {
        this.imdbDescription = imdbDescription;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
        this.metacriticRating = metacriticRating;
        this.rottenTomatoesRating = rottenTomatoesRating;
        this.boxOffice = boxOffice;
        this.duration = duration;
        this.imdbGenre = imdbGenre;
        this.imdbDirector = imdbDirector;
        this.actors = actors;
        this.language = language;
        this.imdbCountry = imdbCountry;
        this.metascore = metascore;
        this.awards = awards;
        this.writer = writer;
        this.released = released;
        this.production = production;
        this.audienceRating = audienceRating;
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
     * @param kinopoiskImage       the image
     * @param imdbDescription the description
     */
    public Movie(String nameEn, String nameRu, String imdbId, String filmId,
                 String shortDesc, String duration, String year, String kVotes,
                 String rating, String kinopoiskImage, String imdbDescription) {
        this.engName = nameEn;
        this.imdbId = imdbId;
        this.kinopoiskId = filmId;
        this.imdbDirector = shortDesc;
        this.imdbDescription = imdbDescription;
        this.rusName = nameRu;
        this.kinopoiskRating = rating;
        this.duration = duration;
        this.kinopoiskImage = kinopoiskImage;
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
    public String getEngName() {
        return engName;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setEngName(String name) {
        this.engName = name;
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
    public String getImdbDescription() {
        return imdbDescription;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setImdbDescription(String description) {
        this.imdbDescription = description;
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

    public String getKinopoiskDescription() {
        return kinopoiskDescription;
    }

    public void setKinopoiskDescription(String kinopoiskDescription) {
        this.kinopoiskDescription = kinopoiskDescription;
    }

    public String getKinopoiskGenre() {
        return kinopoiskGenre;
    }

    public void setKinopoiskGenre(String kinopoiskGenre) {
        this.kinopoiskGenre = kinopoiskGenre;
    }

    public String getKinopoiskCountry() {
        return kinopoiskCountry;
    }

    public void setKinopoiskCountry(String kinopoiskCountry) {
        this.kinopoiskCountry = kinopoiskCountry;
    }

    public String getImdbDistributor() {
        return imdbDistributor;
    }

    public void setImdbDistributor(String imdbDistributor) {
        this.imdbDistributor = imdbDistributor;
    }

    public String getKinopoiskDistributor() {
        return kinopoiskDistributor;
    }

    public void setKinopoiskDistributor(String kinopoiskDistributor) {
        this.kinopoiskDistributor = kinopoiskDistributor;
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
    public String getRusName() {
        return rusName;
    }

    /**
     * Sets eastern name.
     *
     * @param easternName the eastern name
     */
    public void setRusName(String easternName) {
        this.rusName = easternName;
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
    public String getImdbGenre() {
        return imdbGenre;
    }

    /**
     * Sets genre.
     *
     * @param genre the genre
     */
    public void setImdbGenre(String genre) {
        this.imdbGenre = genre;
    }

    /**
     * Gets director.
     *
     * @return the director
     */
    public String getImdbDirector() {
        return imdbDirector;
    }

    /**
     * Sets director.
     *
     * @param director the director
     */
    public void setImdbDirector(String director) {
        this.imdbDirector = director;
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
    public String getImdbCountry() {
        return imdbCountry;
    }

    public String getKinopoiskDirector() {
        return kinopoiskDirector;
    }

    public void setKinopoiskDirector(String kinopoiskDirector) {
        this.kinopoiskDirector = kinopoiskDirector;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setImdbCountry(String country) {
        this.imdbCountry = country;
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
    public String getKinopoiskImage() {
        return kinopoiskImage;
    }

    /**
     * Sets image.
     *
     * @param image the image
     */
    public void setKinopoiskImage(String image) {
        this.kinopoiskImage = image;
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

    public String getImdbPoster() {
        return imdbPoster;
    }

    public void setImdbPoster(String imdbPoster) {
        this.imdbPoster = imdbPoster;
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


    public void addImageToMovie(Image image) {
        if(images == null) {
            images = new HashSet<>();
        }
        images.add(image);
    }



    public Set<MovieReviewSource> getMovieReviewSources() {
        return movieReviewSources;
    }

    public void setMovieReviewSources(Set<MovieReviewSource> movieReviewSources) {
        this.movieReviewSources = movieReviewSources;
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
        return id == movie.id && Objects.equals(engName, movie.engName)
                && Objects.equals(searches, movie.searches)
                && Objects.equals(imdbId, movie.imdbId)
                && Objects.equals(kinopoiskId, movie.kinopoiskId)
                && Objects.equals(imdbDescription, movie.imdbDescription)
                && Objects.equals(imdbRating, movie.imdbRating)
                && Objects.equals(imdbVotes, movie.imdbVotes)
                && Objects.equals(metacriticRating, movie.metacriticRating)
                && Objects.equals(theMovieDbRating, movie.theMovieDbRating)
                && Objects.equals(rottenTomatoesRating, movie.rottenTomatoesRating)
                && Objects.equals(tV_comRating, movie.tV_comRating)
                && Objects.equals(filmAffinityRating, movie.filmAffinityRating)
                && Objects.equals(rusName, movie.rusName)
                && Objects.equals(kinopoiskRating, movie.kinopoiskRating)
                && Objects.equals(boxOffice, movie.boxOffice)
                && Objects.equals(duration, movie.duration)
                && Objects.equals(imdbGenre, movie.imdbGenre)
                && Objects.equals(imdbDirector, movie.imdbDirector)
                && Objects.equals(actors, movie.actors)
                && Objects.equals(language, movie.language)
                && Objects.equals(imdbCountry, movie.imdbCountry)
                && Objects.equals(metascore, movie.metascore)
                && Objects.equals(kinopoiskImage, movie.kinopoiskImage)
                && Objects.equals(year, movie.year)
                && Objects.equals(kinopoiskVotes, movie.kinopoiskVotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, engName, searches, imdbId, kinopoiskId, imdbDescription,
                imdbRating, imdbVotes, metacriticRating, theMovieDbRating,
                rottenTomatoesRating, tV_comRating, filmAffinityRating, rusName,
                kinopoiskRating, boxOffice, duration, imdbGenre, imdbDirector, actors, language,
                imdbCountry, metascore, kinopoiskImage, year, kinopoiskVotes);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + engName + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", kinopoiskId='" + kinopoiskId + '\'' +
                ", description='" + imdbDescription + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", imdbVotes='" + imdbVotes + '\'' +
                ", metacriticRating='" + metacriticRating + '\'' +
                ", theMovieDbRating='" + theMovieDbRating + '\'' +
                ", rottenTomatoesRating='" + rottenTomatoesRating + '\'' +
                ", tV_comRating='" + tV_comRating + '\'' +
                ", filmAffinityRating='" + filmAffinityRating + '\'' +
                ", easternName='" + rusName + '\'' +
                ", kinopoiskRating='" + kinopoiskRating + '\'' +
                ", boxOffice='" + boxOffice + '\'' +
                ", duration='" + duration + '\'' +
                ", genre='" + imdbGenre + '\'' +
                ", director='" + imdbDirector + '\'' +
                ", actors='" + actors + '\'' +
                ", language='" + language + '\'' +
                ", country='" + imdbCountry + '\'' +
                ", metascore='" + metascore + '\'' +
                ", image='" + kinopoiskImage + '\'' +
                ", year='" + year + '\'' +
                ", isAdded='" + isAddedToUserList + '\'' +
                '}';
    }
}