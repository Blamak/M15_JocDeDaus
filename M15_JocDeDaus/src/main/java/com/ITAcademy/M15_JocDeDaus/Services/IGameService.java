package com.ITAcademy.M15_JocDeDaus.Services;

import java.math.BigDecimal;
import java.util.List;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Game;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;

public interface IGameService {
	
	public Game saveGame(long player_id);
	
	public List<Game> gamesByPlayer(long player_id);
	
	public void deleteGamesByPlayer(long player_id);
	
	public BigDecimal calculateWinRate(long player_id);


}
