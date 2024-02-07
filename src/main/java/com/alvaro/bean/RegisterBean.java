package com.alvaro.bean;

import com.alvaro.model.orm.SPAuthority;
import com.alvaro.model.orm.User;
import com.alvaro.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Component("registerBean")
@Scope("view")
@Slf4j
public class RegisterBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Getter @Setter
    private User user;
    @Getter @Setter
    private SPAuthority selectedAuthority;
    private final transient UserService userService;
    @Getter
    private final List<SPAuthority> authorities;
    @Autowired
    public RegisterBean(final UserService userService){
        this.userService = userService;
        this.authorities = userService.findAllAuthorities();
    }

    @PostConstruct
    public void init(){
        this.user = new User();
    }

    public String register(){
        final User saved = this.userService.register(this.user, this.selectedAuthority);
        return saved != null ? "index.faces" : "register.faces?error=true";
    }

}
