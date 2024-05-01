package com.ebingo.api.Controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebingo.api.Models.GameModel;
import com.ebingo.api.Services.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/Bingo")
public class GameController {
    private GameService gameService;

    GameController(GameService gameService)
    {
        this.gameService = gameService;
    }

    @GetMapping("")
    public ResponseEntity<List<GameModel>> GetAll() {
        List<GameModel> games = gameService.GetAll();

        if(!games.isEmpty())
        {
            return ResponseEntity.ok(games);
        }

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/bcode={gameCode}")
    public ResponseEntity<GameModel> GetById(@PathVariable String gameCode) {
        try {
            GameModel game = gameService.GetById(gameCode);
            return ResponseEntity.ok(game);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("")
    public ResponseEntity<GameModel> Create(@RequestBody GameModel game)
    {
        return ResponseEntity.ok(gameService.Create());
    }
}
