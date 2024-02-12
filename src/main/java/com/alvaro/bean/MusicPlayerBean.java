package com.alvaro.bean;

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
public class MusicPlayerBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Setter @Getter
    private float volume;
    @Setter @Getter
    private float playTime;
    @Getter
    private boolean replay;

    public void setReplay() {
        this.replay = !this.replay;
    }
}
