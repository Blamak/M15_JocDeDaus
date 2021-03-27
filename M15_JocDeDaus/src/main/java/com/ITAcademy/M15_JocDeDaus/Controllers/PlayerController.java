package com.ITAcademy.M15_JocDeDaus.Controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ITAcademy.M15_JocDeDaus.DTO.Message;
import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Services.IPlayerService;

@RestController
@RequestMapping("/players")
public class PlayerController {

	@Autowired
	private IPlayerService playerService;

	@PostMapping("") // CREATE NEW PLAYER
	public ResponseEntity<Message> newPlayer(@RequestBody PlayerDTO playerDTO) {
		try {
			 PlayerDTO player = playerService.savePlayer(playerDTO);

			return new ResponseEntity<Message>(
					new Message("Player created successfully!", Arrays.asList(player), ""), HttpStatus.OK);
		} catch (DataIntegrityViolationException e1) { // Handle duplicate name error
			return new ResponseEntity<Message>(
					new Message("Another Player has this name already!", null, e1.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("New Player post failed!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("") // READ ALL PLAYERS
	public ResponseEntity<Message> listPlayers() {
		try {
			List<PlayerDTO> playersList = playerService.getAllPlayers();

			return new ResponseEntity<Message>(new Message("Players' list retrieved", playersList, ""), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Failed to get Players' list!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/{player_id}") // UPDATE PLAYER NAME
	public ResponseEntity<Message> updatePlayer(@RequestBody PlayerDTO playerDTO, @PathVariable long player_id) {
		try {
			if (playerService.checkPlayerExists(player_id)) {
				PlayerDTO player = playerService.getPlayerByID(player_id);

				player.setName(playerDTO.getName());

				// save change to database
				playerService.replacePlayerName(player);

				return new ResponseEntity<Message>(
						new Message("Player name successfully updated!", Arrays.asList(player), ""), HttpStatus.OK);
			} else {
				return new ResponseEntity<Message>(
						new Message("Player with id = " + player_id + " not found!!", null, ""), HttpStatus.NOT_FOUND);
			}
		} catch (DataIntegrityViolationException e1) { // Handle duplicate name error
			return new ResponseEntity<Message>(
					new Message("Another Player has this name already!", null, e1.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Failed update Player!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
