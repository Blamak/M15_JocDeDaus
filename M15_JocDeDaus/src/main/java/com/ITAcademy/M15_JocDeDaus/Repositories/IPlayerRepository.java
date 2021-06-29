package com.ITAcademy.M15_JocDeDaus.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ITAcademy.M15_JocDeDaus.Entities.Player;

@Repository
public interface IPlayerRepository extends MongoRepository<Player, String> {

	Player findByName(String name);

}
