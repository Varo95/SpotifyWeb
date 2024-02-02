package com.alvaro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
@Entity
@Table(name = "songs")
@Getter
@Setter
public class Song implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double duration;
    @ManyToOne
    private Genre genre;
    @ManyToOne
    private Disk disk;
    private long nPlays;
    private String songURL;

    public Song(){

    }
}
