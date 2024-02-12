package com.alvaro.model.orm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;
@Entity
@Table(name = "authorities")
@NamedQuery(name = "SPAuthority.findByAuthority", query = "SELECT a FROM SPAuthority a WHERE a.authority = ?1")
@NamedQuery(name = "SPAuthority.findByAuthority.count", query = "SELECT COUNT(a) FROM SPAuthority a WHERE a.authority = ?1")
@Getter
@Setter
public class SPAuthority implements GrantedAuthority, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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

    public String toToGUI(){
        return StringUtils.capitalize(this.authority.replace("ROLE_","").toLowerCase());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SPAuthority authority1 = (SPAuthority) o;
        if (this.id != authority1.id) return false;
        return this.authority.equals(authority1.authority);
    }

    @Override
    public int hashCode() {
        int result = (int) (this.id ^ (this.id >>> 32));
        result = 31 * result + this.authority.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("SPAuthority[%d, %s]", this.id, this.authority);
    }
}
