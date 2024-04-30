package com.ebingo.api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ebingo.api.Models.GameModel;

@Repository
public interface GameRepository extends JpaRepository<GameModel, String>{
}
