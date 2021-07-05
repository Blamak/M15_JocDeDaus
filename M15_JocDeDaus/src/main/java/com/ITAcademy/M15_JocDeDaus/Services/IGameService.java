package com.ITAcademy.M15_JocDeDaus.Services;

import java.util.List;

import com.ITAcademy.M15_JocDeDaus.DTO.GameDTO;

public interface IGameService {
	
	public GameDTO playGame(long player_id);
	public List<GameDTO> gamesByPlayer(long player_id);
	public void deleteGamesByPlayer(long player_id);
	public double calculateWinRate(long player_id);
	public Boolean checkNoGames();


}
