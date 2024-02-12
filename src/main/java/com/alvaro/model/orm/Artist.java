package com.alvaro.model.orm;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
@Entity
@Table(name = "artists")
@NamedQuery(name = "Artist.findByName", query = "SELECT a FROM Artist a WHERE a.name LIKE ?1")
@NamedQuery(name = "Artist.findByName.count", query = "SELECT COUNT(a) FROM Artist a WHERE a.name LIKE ?1")
@Getter
@Setter
public class Artist implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String nationality;
    private String photoURL;
    @OneToMany(mappedBy = "artist")
    private List<Disk> disks;
    public Artist(){

    }
}
