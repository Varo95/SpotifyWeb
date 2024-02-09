package com.alvaro.model.orm;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQuery;
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
@NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = ?1")
@NamedQuery(name = "User.findByName.count", query = "SELECT COUNT(u) FROM User u WHERE u.name = ?1")
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
    @JoinTable(name = "subscriptions",
            joinColumns = @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id", nullable = false, referencedColumnName = "id"))
    @ManyToMany
    private List<PlayList> subsPlaylists;
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false, referencedColumnName = "id")})
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<SPAuthority> authorities;
    private boolean disabled;

    public User(final String name, final String passwd, final String photoURL, final List<SPAuthority> authorities, final boolean disabled) {
        this.name = name;
        this.passwd = passwd;
        this.photoURL = photoURL;
        this.authorities = authorities;
        this.disabled = disabled;
    }

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
        return !this.disabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.disabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.disabled;
    }

    @Override
    public boolean isEnabled() {
        return !this.disabled;
    }

    @Override
    public String toString(){
        return String.format("User[%d, %s, %s]", this.id, this.name, this.disabled ? "disabled" : "enabled");
    }
}
