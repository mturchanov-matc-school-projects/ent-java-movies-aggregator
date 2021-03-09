package com.movie_aggregator.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
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

    private int number;

    public Search() {
    }

    public Search(String name) {
        this.name = name;
    }

    public Search(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }


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
