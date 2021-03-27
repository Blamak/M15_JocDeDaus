package com.ITAcademy.M15_JocDeDaus.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ITAcademy.M15_JocDeDaus.Entities.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{

	void save(Optional<Player> player);
	
}
