package com.ebingo.api.Models;

import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bingo_cards")
public class BingoCard {
    @Id
    @Column(name = "playcard_token")
    private String token;

    @ElementCollection
    @Column(name = "card_numbers")
    private Map<String, List<Integer>> card;

    @Column(name = "code")
    private String gameCode;
}
