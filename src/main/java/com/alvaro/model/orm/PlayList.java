package com.alvaro.model.orm;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "playlists")
@NamedQuery(name = "PlayList.findByName", query = "SELECT p FROM PlayList p WHERE p.name LIKE ?1")
@NamedQuery(name = "PlayList.findByName.count", query = "SELECT COUNT(p) FROM PlayList p WHERE p.name LIKE ?1")
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
    @Transient
    private long nSubs;
    @OneToMany
    private List<Song> songs;
    @OneToMany(mappedBy = "playList")
    private List<Comment> comments;
    @ManyToMany(mappedBy = "subsPlaylists")
    private List<User> subscribers;

    public PlayList(){

    }

    public long getNSubs(){
        return this.subscribers.size();
    }
}
