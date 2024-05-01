package com.ebingo.api.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebingo.api.Models.BingoCard;
import com.ebingo.api.Repositories.BingoCardRepository;

@Service
public class BingoCardService {
    @Autowired
    private BingoCardRepository cardRepository;

    public BingoCardService(BingoCardRepository cardRepository)
    {
        this.cardRepository = cardRepository;
    }

    public List<BingoCard> GetAll()
    {
        return cardRepository.findAll();
    }

    // public BingoCard GetById(int id)
    // {
    //     return cardRepository.findById(id).orElseThrow();
    // }

    public BingoCard Create()
    {
        BingoCard card = new BingoCard();
        String token = generateToken();
        card.setToken(token);

        Map<String, List<Integer>> cardNumbers = new HashMap<>();
        cardNumbers.put("B", generateRandomNumbers(1, 15, 5));
        cardNumbers.put("I", generateRandomNumbers(16, 30, 5));
        cardNumbers.put("N", generateRandomNumbers(31, 45, 4));
        cardNumbers.put("G", generateRandomNumbers(46, 60, 5));
        cardNumbers.put("O", generateRandomNumbers(61, 75, 5));

        card.setCard(cardNumbers);

        return cardRepository.save(card);
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
}
