package com.alvaro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "playlists")
@Getter
@Setter
public class PlayList implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @ManyToOne
    private User creator;
    private long nSubs;
    @OneToMany
    private List<Song> songs;
    @OneToMany(mappedBy = "playList")
    private List<Comment> comments;
    @OneToMany
    private List<User> subscribers;

    public PlayList(){

    }
}
