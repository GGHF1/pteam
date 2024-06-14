package com.store.pteam.service;

import com.store.pteam.model.Game;
import com.store.pteam.model.GameLibrary;
import com.store.pteam.model.User;
import com.store.pteam.repository.GameLibraryRepository;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameLibraryService {

    @Autowired
    private GameLibraryRepository gameLibraryRepository;

    public GameLibrary findByUser(User user) {
        return gameLibraryRepository.findByUser(user);
    }

    public void addGameToLibrary(User user, Game game) {
        GameLibrary gameLibrary = findByUser(user);
        if (gameLibrary == null) {
            gameLibrary = new GameLibrary();
            gameLibrary.setUser(user);
        }
        gameLibrary.getGames().add(game);
        gameLibraryRepository.save(gameLibrary);
    }

    public List<Game> getGamesByUser(User user) {
        GameLibrary gameLibrary = gameLibraryRepository.findByUser(user);
        if (gameLibrary != null) {
            return gameLibrary.getGames();
        }
        return Collections.emptyList(); // Return an empty list if no game library is found
    }

    public boolean isGameInLibrary(User user, Game game) {
        GameLibrary gameLibrary = findByUser(user);
        if (gameLibrary != null) {
            return gameLibrary.getGames().contains(game);
        }
        return false; // Return false if no game library is found
    }
}
