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
    // @Column(name = "bingo_numbers_json", columnDefinition = "TEXT")
    // private String bingoNumbersJson;

    // public void setBingoNumbers(List<Integer> bingoNumbers) {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     try {
    //         this.bingoNumbersJson = objectMapper.writeValueAsString(bingoNumbers);
    //     } catch (JsonProcessingException e) {
    //         e.printStackTrace();
    //     }
    // }

    // public List<Integer> getBingoNumbers() {
    //     if (this.bingoNumbersJson == null) {
    //         return Collections.emptyList();
    //     }
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     try {
    //         return objectMapper.readValue(this.bingoNumbersJson, new TypeReference<List<Integer>>() {});
    //     } catch (JsonProcessingException e) {
    //         // Handle deserialization error
    //         e.printStackTrace();
    //         return Collections.emptyList();
    //     }
    // }
}
