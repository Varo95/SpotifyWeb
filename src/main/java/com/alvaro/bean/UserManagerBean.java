package com.alvaro.bean;

import com.alvaro.model.orm.User;
import com.alvaro.service.UserService;
import com.alvaro.utils.SessionTool;
import com.alvaro.utils.Tools;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component("userManagerBean")
@Scope("view")
@Slf4j
public class UserManagerBean extends UploaderBean implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final transient UserService userService;
    private final transient SessionTool sessionTool;
    @Getter
    private final User loggedUser;

    @Autowired
    public UserManagerBean(final UserService userService,
                           @Value("${earthsound.assets.user.path}") final String userPath,
                           final SessionTool sessionTool){
        super(userPath);
        this.userService = userService;
        this.sessionTool = sessionTool;
        this.loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void modifyCurrentUser(){
        final String fileExtension = this.getUploadedFile().getFileName().substring(this.getUploadedFile().getFileName().lastIndexOf("."));
        this.loggedUser.setPhotoURL(this.writeFileAndGetPath(this.loggedUser.getUsername()+fileExtension));
        final UserDetails updated = this.userService.updatePassword(this.loggedUser, this.loggedUser.getPassword());
        if(updated != null){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(updated,"{noop}" + updated.getPassword(), updated.getAuthorities()));
            this.sessionTool.setProfilePhoto(Tools.getStreamedContent(this.loggedUser.getPhotoURL()));
            //TODO update profile-photo in the view
            log.info("User updated");
        }
    }

}
