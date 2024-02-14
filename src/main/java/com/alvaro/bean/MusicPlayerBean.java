package com.alvaro.bean;

import com.alvaro.model.orm.Song;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component("musicPlayerBean")
@Scope("view")
@Slf4j
@Getter
public class MusicPlayerBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Setter
    private float volume;
    @Setter
    private float playTime;
    private boolean replay;
    private Song currentSong;

    public void setReplay() {
        this.replay = !this.replay;
    }
}
