package com.ITAcademy.M15_JocDeDaus.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Game;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;

public interface IGameRepository extends JpaRepository<Game, Long>{
	
	List<Game> findByPlayer(Player player);

}
