package com.ITAcademy.M15_JocDeDaus.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ITAcademy.M15_JocDeDaus.Entities.Game;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;

@Repository
public interface IGameRepository extends CrudRepository<Game, String>{

	List<Game> findByPlayerId(String player_id);
	
}
