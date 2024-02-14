package com.alvaro.utils;

import com.alvaro.model.orm.User;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is a session scoped bean that holds the user's photo and username and another variables for session
 */
@Component("sessionTool")
@SessionScope
@Getter
@Setter
public class SessionTool implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private transient StreamedContent profilePhoto;
    private String userName;
    private transient StreamedContent diskPhoto;

    public SessionTool(){
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.userName = user.getUsername();
        this.profilePhoto = Tools.getStreamedContent(user.getPhotoURL());
    }
}
