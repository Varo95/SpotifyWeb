package com.alvaro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
@Entity
public class SPAuthority implements GrantedAuthority, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(unique = true)
    private final String authority;

    public SPAuthority(){
        this.authority = "";
    }

    public SPAuthority(final String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
