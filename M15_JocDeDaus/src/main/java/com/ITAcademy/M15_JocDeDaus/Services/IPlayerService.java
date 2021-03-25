package com.ITAcademy.M15_JocDeDaus.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;

public interface IPlayerService {
	
	public ResponseEntity<PlayerDTO> savePlayer(PlayerDTO playerDTO);
	
	public List<PlayerDTO> getAllPlayers();

}
