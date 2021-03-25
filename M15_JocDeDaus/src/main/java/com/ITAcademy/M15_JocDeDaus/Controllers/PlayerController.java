package com.ITAcademy.M15_JocDeDaus.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Services.IPlayerService;

@RestController
@RequestMapping("/players")
public class PlayerController {
	
	@Autowired
	private IPlayerService playerService;
	
	@PostMapping("") // CREATE
	public ResponseEntity<PlayerDTO> newPlayer(@RequestBody PlayerDTO playerDTO) {
		return playerService.savePlayer(playerDTO);
	}
	
	@GetMapping("")
	public List<PlayerDTO> listPlayers() {
		return playerService.getAllPlayers();
	}

}
