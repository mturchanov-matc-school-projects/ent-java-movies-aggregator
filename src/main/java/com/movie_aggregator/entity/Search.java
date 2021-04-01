package com.movie_aggregator.entity;

import javax.persistence.*;
import java.util.*;

/**
 * The type Search.
 *
 * @author mturchanov
 */
@Table(name = "search")
@Entity(name = "Search")
public class Search {
    @Id
    private int id;

    @Column(unique = true, nullable = false)
    private String name;
    //@ManyToMany(cascade = CascadeType.ALL)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "movie_search",
            joinColumns = @JoinColumn(name = "search_id"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "movie_id") //write how bridge table get connected with other target table/entity
    )
    private Set<Movie> movies;

    private int number = 1;

    /**
     * Instantiates a new Search.
     */
    public Search() {
    }

    /**
     * Instantiates a new Search.
     *
     * @param name the name
     */
    public Search(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Search.
     *
     * @param id   the id
     * @param name the name
     */
    public Search(int id, String name) {
        this.id = id;
        this.name = name;
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
     * Gets number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets number.
     *
     * @param number the number
     */
    public void setNumber(int number) {
        this.number = number;
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
     * Gets movies.
     *
     * @return the movies
     */
    public Set<Movie> getMovies() {
        return movies;
    }

    /**
     * Sets movies.
     *
     * @param movies the movies
     */
    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Add movie to search.
     *
     * @param movie the movie
     */
    public void addMovieToSearch(Movie movie) {
        if(movies == null) {
            movies = new HashSet<>();
        }
        movies.add(movie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Search search = (Search) o;
        return id == search.id && Objects.equals(name, search.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Search{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
