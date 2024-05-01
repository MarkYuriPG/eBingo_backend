package com.ebingo.api.Services;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebingo.api.Models.GameModel;
import com.ebingo.api.Repositories.GameRepository;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository)
    {
        this.gameRepository = gameRepository;
    }

    public List<GameModel> GetAll(){
        return gameRepository.findAll();
    }

    public GameModel GetById(String code)
    {
        return gameRepository.findById(code).orElseThrow();
    }

    public GameModel Create() {
        GameModel game = new GameModel();
        game.setGameCode(generateGameCode());   
        return gameRepository.save(game);
    }

    private String generateGameCode() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}
