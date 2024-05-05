package com.ebingo.api.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebingo.api.Models.BingoCard;
import com.ebingo.api.Models.GameModel;
import com.ebingo.api.Repositories.BingoCardRepository;
import com.ebingo.api.Repositories.GameRepository;

@Service
public class BingoCardService {
    @Autowired
    private final BingoCardRepository cardRepository;
    private final GameRepository gameRepository;

    public BingoCardService(BingoCardRepository cardRepository, GameRepository gameRepository)
    {
        this.cardRepository = cardRepository;
        this.gameRepository = gameRepository;
    }

    public List<BingoCard> GetAll()
    {
        return cardRepository.findAll();
    }

    // public BingoCard GetById(int id)
    // {
    //     return cardRepository.findById(id).orElseThrow();
    // }

    public BingoCard Create(String gamecode)
    {
        try {
            if(gameRepository.findById(gamecode).isPresent())
            {
                BingoCard card = new BingoCard();
                String token = generateToken();
                card.setToken(token);
                card.setGameCode(gamecode);

                Map<String, List<Integer>> cardNumbers = new HashMap<>();
                cardNumbers.put("B", generateRandomNumbers(1, 15, 5));
                cardNumbers.put("I", generateRandomNumbers(16, 30, 5));
                cardNumbers.put("N", generateRandomNumbers(31, 45, 4));
                cardNumbers.put("G", generateRandomNumbers(46, 60, 5));
                cardNumbers.put("O", generateRandomNumbers(61, 75, 5));

                card.setCard(cardNumbers);

                return cardRepository.save(card);
            }
        } catch (NoSuchElementException e) {
            return null;
        }
        return null;
    }

    private String generateToken() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

    private List<Integer> generateRandomNumbers(int min, int max, int count) {
        Random random = new Random();
        List<Integer> numbers = new ArrayList<>();
        while (numbers.size() < count) {
            int num = random.nextInt(max - min + 1) + min;
            if (!numbers.contains(num)) {
                numbers.add(num);
            }
        }
        return numbers;
    }

    public int checkWin(String token) {
        try {
            BingoCard card = cardRepository.findById(token).orElse(null);
            if (card == null) {
                // Token not found
                return 0;
            }
    
            GameModel game = gameRepository.findById(card.getGameCode()).orElseThrow();
            List<Integer> rolledNumbers = game.getRolledNumbers();
    
            Map<String, List<Integer>> cardNumbers = card.getCard();
    
            // Check for winning combinations
            if (checkHorizontal(cardNumbers, rolledNumbers) ||
                checkVertical(cardNumbers, rolledNumbers) ||
                checkDiagonal(cardNumbers, rolledNumbers)) {
                // Bingo! Winning combination found
                return 1;
            }
        } catch (NoSuchElementException e) {
            return 0;
        }
        // No winning combination found
        return 0;
    }
    

    private boolean checkHorizontal(Map<String, List<Integer>> cardNumbers, List<Integer> rolledNumbers) {
        // Check for a horizontal line
        for (List<Integer> numbers : cardNumbers.values()) {
            if (rolledNumbers.containsAll(numbers)) {
                // All numbers in this row are present in the rolled numbers, indicating a winning horizontal line
                return true;
            }
        }
        return false;
    }
    
    private boolean checkVertical(Map<String, List<Integer>> cardNumbers, List<Integer> rolledNumbers) {
        // Check for a vertical line
        for (int i = 0; i < 5; i++) {
            int count = 0;
            for (List<Integer> numbers : cardNumbers.values()) {
                // Ensure the current column index 'i' is within the range of numbers list
                if (i < numbers.size() && rolledNumbers.contains(numbers.get(i))) {
                    count++;
                }
            }
            if (count == 5) {
                // All columns have their numbers present in the rolled numbers, indicating a winning vertical line
                return true;
            }
        }
        return false;
    }
    
    private boolean checkDiagonal(Map<String, List<Integer>> cardNumbers, List<Integer> rolledNumbers) {
        // Check for a diagonal line
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < 5; i++) {
            String key1 = String.valueOf((char) ('B' + i));
            String key2 = String.valueOf((char) ('B' + i));
            
            List<Integer> list1 = cardNumbers.get(key1);
            List<Integer> list2 = cardNumbers.get(key2);
            
            if (list1 != null && rolledNumbers.contains(list1.get(i))) {
                count1++;
            }
            if (list2 != null && rolledNumbers.contains(list2.get(4 - i))) {
                count2++;
            }
        }
        if (count1 == 5 || count2 == 5) {
            // Either of the diagonals has all numbers present in the rolled numbers, indicating a winning diagonal line
            return true;
        }
        return false;
    }
    
}
