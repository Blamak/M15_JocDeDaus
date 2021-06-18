package com.ITAcademy.M15_JocDeDaus.Repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.ITAcademy.M15_JocDeDaus.Entities.Player;

//@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface IPlayerRepository extends MongoRepository<Player, Long> {
	
	List<Player> findByName(@Param("name") String name);

	}
