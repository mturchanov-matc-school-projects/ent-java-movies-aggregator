package com.movie_aggregator.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author mturchanov
 */

@Table(name = "search")
@Entity(name = "Search")
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "movie_search",
            joinColumns = @JoinColumn(name = "search_id"), //write how bridge table get connected with this source table/entity
            inverseJoinColumns = @JoinColumn(name = "movie_id") //write how bridge table get connected with other target table/entity
    )
    private List<Movie> movies;

    public Search() {
    }

    public Search(String name) {
        this.name = name;
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

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void addMovieToSearch(Movie movie) {
        if(movies == null) {
            movies = new ArrayList<>();
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
