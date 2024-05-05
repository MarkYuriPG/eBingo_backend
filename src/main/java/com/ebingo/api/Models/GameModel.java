package com.ebingo.api.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="games")
public class GameModel {
    @Id
    @Column(name = "code")
    private String gameCode;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_card", referencedColumnName = "code")
    private List<BingoCard> cards;

    @ElementCollection
    @Column(name = "rolled_numbers")
    private List<Integer> rolledNumbers;
}
