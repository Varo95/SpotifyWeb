package com.alvaro.repositories;

import com.alvaro.model.orm.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {
    Optional<PlayList> findByName(final String name);
}
