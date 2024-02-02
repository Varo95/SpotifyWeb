package com.alvaro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
@Entity
@Table(name = "_user")
@Getter
@Setter
public class User implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String passwd;
    private String photoURL;
    @OneToMany
    private List<PlayList> subsPlaylists;
    @ManyToMany
    private List<SPAuthority> authorities;
    private boolean disabled;

    public User(){

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.passwd;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.disabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.disabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.disabled;
    }

    @Override
    public boolean isEnabled() {
        return !this.disabled;
    }
}
