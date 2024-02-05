package com.alvaro.bean;

import com.alvaro.model.User;
import com.alvaro.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component("registerBean")
@Scope("view")
@Slf4j
public class RegisterBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Getter @Setter
    private User user;
    private final transient UserService userService;
    @Autowired
    public RegisterBean(final UserService userService){
        this.userService = userService;
    }

    @PostConstruct
    public void init(){
        this.user = new User();
    }

    public void register(){
        final User saved = this.userService.register(this.user);
        log.info("User registered: {}", saved);
    }

}
