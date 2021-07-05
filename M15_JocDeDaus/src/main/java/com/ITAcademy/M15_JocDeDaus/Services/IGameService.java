package com.ITAcademy.M15_JocDeDaus.Services;

import java.math.BigDecimal;
import java.util.List;

import com.ITAcademy.M15_JocDeDaus.DTO.GameDTO;

public interface IGameService {
	
	public GameDTO playGame(String player_id);
	public List<GameDTO> gamesByPlayer(String player_id);
	public void deleteGamesByPlayer(String player_id);
	public double calculateWinRate(String player_id);
	public Boolean checkNoGames();


}
