package com.ITAcademy.M15_JocDeDaus.Services;

import java.util.List;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;

public interface IPlayerService {
	
	public PlayerDTO savePlayer(PlayerDTO playerDTO);
	public List<PlayerDTO> getAllPlayers();
	public void replacePlayer(PlayerDTO playerDTO);
	public PlayerDTO getPlayerByID(String player_id);
	public boolean checkNameDuplicated(String name);

	public Player mapDTOtoEntity(PlayerDTO playerDTO);
	public PlayerDTO mapEntitytoDTO(Player playerEntity);
	
	
	

}
