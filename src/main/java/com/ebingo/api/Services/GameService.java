package com.ebingo.api.Services;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebingo.api.Models.BingoCard;
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

    public List<BingoCard> GetCards(String code)
    {
        GameModel game = GetById(code);
        if (game != null) {
            return game.getCards();
        } else {
            return null;
        }
    }

    public GameModel Create() {
        GameModel game = new GameModel();
        game.setGameCode(generateGameCode());
        try {
            GetById(game.getGameCode());
            return null;
        } catch (NoSuchElementException e) {
            return gameRepository.save(game);
        }
    }

    public String getGameBoard(String code) {

        try {
            GameModel game = GetById(code);

            return "<html>\n" +
                    "<head>\n" +
                    "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\"\n" +
                    "        integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n" +
                    "    <script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\"\n" +
                    "        integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\">\n" +
                    "    </script>\n" +
                    "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\"\n" +
                    "        integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\">\n" +
                    "    </script>\n" +
                    "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\"\n" +
                    "        integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\">\n" +
                    "    </script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <a href='newbingogame.php'><button type=\"button\" class=\"btn btn-primary\">Create New Game</button></a><br>\n" +
                    "        <div class=\"container\" style=\"padding: 10px\">\n" +
                    "            <h1> Game Code: " + game.getGameCode() + "</h1>\n" +
                    "            <button style=\"margin:2px\" type=\"button\" class=\"btn btn-info\">B</button>\n" +
                    "            <!-- Add other buttons here -->\n" +
                    "        </div>\n" +
                    "        <a href='?bcode=" + game.getGameCode() + "&dr=1'><button type=\"button\" class=\"btn btn-primary\">Next Ball</button></a>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public int rollBall(String code) {
        // Generate a random number between 1 and 75
        // Get the game by its code
        GameModel game = GetById(code);

        if (game.getRolledNumbers().size() >= 75) {
            throw new IllegalStateException("All numbers have been rolled");
        }
    
        int rolledNumber;
        // Add the rolled number to the game's rolledNumbers list
        do {
            rolledNumber = (int) (Math.random() * 75) + 1;
        } while (game.getRolledNumbers().contains(rolledNumber));

        game.getRolledNumbers().add(rolledNumber);
        // Save the updated game
        gameRepository.save(game);
        // Return the rolled number
        return rolledNumber;
    }

    private String generateGameCode() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}
