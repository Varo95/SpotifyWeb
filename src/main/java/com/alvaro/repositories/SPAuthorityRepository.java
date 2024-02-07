package com.alvaro.repositories;

import com.alvaro.model.orm.SPAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SPAuthorityRepository extends JpaRepository<SPAuthority, Long> {
    Optional<SPAuthority> findByAuthority(final String authority);
}
