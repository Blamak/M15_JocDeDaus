package com.ITAcademy.M15_JocDeDaus.Services;

import java.math.BigDecimal;
import java.util.List;

import com.ITAcademy.M15_JocDeDaus.DTO.GameDTO;

public interface IGameService {
	
	public GameDTO saveGame(String player_id);
	public List<GameDTO> gamesByPlayer(String player_id);
//	public void deleteGamesByPlayer(long player_id);
	public BigDecimal calculateWinRate(String player_id);


}
