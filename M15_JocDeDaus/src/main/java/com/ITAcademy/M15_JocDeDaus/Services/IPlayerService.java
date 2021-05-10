package com.ITAcademy.M15_JocDeDaus.Services;

import java.util.List;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;

public interface IPlayerService {
	
	public PlayerDTO savePlayer(PlayerDTO playerDTO);
	public List<PlayerDTO> getAllPlayers();
	public PlayerDTO replacePlayerName(PlayerDTO playerDTO);
	public PlayerDTO getPlayerByID(long player_id);
	public boolean checkPlayerExists(long player_id);
	
	public Player mapDTOtoEntity(PlayerDTO playerDTO);
	public PlayerDTO mapEntitytoDTO(Player playerEntity);
	
	
	

}
