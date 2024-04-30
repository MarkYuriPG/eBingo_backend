package com.ebingo.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebingo.api.Repositories.BingoCardRepository;

@Service
public class BingoCardService {
    @Autowired
    private BingoCardRepository cardRepository;

    public BingoCardService(BingoCardRepository cardRepository)
    {
        this.cardRepository = cardRepository;
    }
}
