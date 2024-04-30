package com.ebingo.api.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="games")
public class GameModel {
    @Id
    @Column(name = "code")
    private String gameCode;
}
