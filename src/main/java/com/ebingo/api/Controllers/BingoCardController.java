package com.ebingo.api.Controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ebingo.api.Models.BingoCard;
import com.ebingo.api.Services.BingoCardService;
import com.ebingo.api.Services.GameService;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/card")
public class BingoCardController {
    private BingoCardService cardService;
    private GameService gameService;

    public BingoCardController(BingoCardService cardService, GameService gameService)
    {
        this.cardService = cardService;
        this.gameService = gameService;
    }

    @GetMapping("")
    public ResponseEntity<List<BingoCard>> GetAll() {
        List<BingoCard> cards = cardService.GetAll();
        if(!cards.isEmpty())
        {
            return ResponseEntity.ok(cards);
        }
        return ResponseEntity.noContent().build();
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<BingoCard> GetById(@PathVariable int id) {
    //     try {
    //         return ResponseEntity.ok(cardService.GetById(id));
    //     } catch (NoSuchElementException e) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @PostMapping("/bcode={gameCode}")
    // public String postMethodName(@RequestBody BingoCard card, @PathVariable String gameCode) {
        
    //     return entity;
    // }
    
    @GetMapping("/bcode={gameCode}")
    public ResponseEntity<BingoCard> GetCard(@PathVariable String gameCode) {
        if (gameService.GetById(gameCode) == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            BingoCard card = cardService.Create(gameCode);
            return ResponseEntity.ok(card);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/checkwin/playtoken={token}")
    public ResponseEntity<Integer> checkWin(@PathVariable String token)
    {
        int result = cardService.checkWin(token);
        return ResponseEntity.ok(result);
    }
}
