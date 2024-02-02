package com.alvaro.bean;

import com.alvaro.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.faces.view.ViewScoped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component
@ViewScoped
@Slf4j
public class RegisterBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private User user;

    @PostConstruct
    public void init(){
        this.user = new User();
    }

    @PreDestroy
    public void destroy(){
        this.user = null;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
