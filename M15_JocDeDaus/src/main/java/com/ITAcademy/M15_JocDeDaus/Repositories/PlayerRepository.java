package com.ITAcademy.M15_JocDeDaus.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ITAcademy.M15_JocDeDaus.Entities.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{
	
}
