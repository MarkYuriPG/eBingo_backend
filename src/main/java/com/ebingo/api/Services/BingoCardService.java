package com.ebingo.api.Services;

import java.util.List;

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

    public BingoCard GetById(int id)
    {
        return cardRepository.findById(id).orElseThrow();
    }
}
