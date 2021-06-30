package com.ITAcademy.M15_JocDeDaus.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ITAcademy.M15_JocDeDaus.Entities.Game;

@Repository
public interface IGameRepository extends MongoRepository<Game, String>{

	List<Game> findByPlayerId(String player_id);
	
}
