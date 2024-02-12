package com.alvaro.service;

import com.alvaro.model.orm.Artist;
import com.alvaro.model.orm.PlayList;
import com.alvaro.repositories.ArtistRepository;
import com.alvaro.repositories.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mainService")
public class MainService {
    private final ArtistRepository artistRepository;
    private final PlayListRepository playListRepository;

    @Autowired
    public MainService(final ArtistRepository artistRepository, final PlayListRepository playListRepository) {
        this.artistRepository = artistRepository;
        this.playListRepository = playListRepository;
    }

    public List<Artist> getAllArtists() {
        return this.artistRepository.findAll();
    }

    public Artist getArtistByName(final String name) {
        return this.artistRepository.findByName(name).orElse(null);
    }

    public List<PlayList> getAllPlayLists() {
        return this.playListRepository.findAll();
    }

    public PlayList getPlayListByName(final String name) {
        return this.playListRepository.findByName(name).orElse(null);
    }
}
