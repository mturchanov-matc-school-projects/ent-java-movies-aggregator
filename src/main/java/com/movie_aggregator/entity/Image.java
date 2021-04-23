package com.movie_aggregator.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * @author mturchanov
 */

@Table(name = "images")
@Entity(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id")
    private int id;
    @Column
    private String image;
    //@ManyToOne
    //@JoinColumn(name="movie_id", nullable=false)
    //private Movie movie;


    public Image() {
    }

    public Image(String image) {
        this.image = image;
    }

    public Image(int id, String image) {
        this.id = id;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
