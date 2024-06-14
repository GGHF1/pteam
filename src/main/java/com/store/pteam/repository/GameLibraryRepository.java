package com.store.pteam.repository;

import com.store.pteam.model.GameLibrary;
import com.store.pteam.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameLibraryRepository extends JpaRepository<GameLibrary, Long> {
    GameLibrary findByUser(User user);
}
