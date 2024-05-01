package com.ebingo.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebingo.api.Models.BingoCard;

@Repository
public interface BingoCardRepository extends JpaRepository<BingoCard, String>{
}