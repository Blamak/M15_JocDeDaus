package com.ITAcademy.M15_JocDeDaus.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils.Message;
import com.ITAcademy.M15_JocDeDaus.DTO.GameDTO;
import com.ITAcademy.M15_JocDeDaus.Services.IGameService;
import com.ITAcademy.M15_JocDeDaus.Services.IPlayerService;

@RestController
@RequestMapping("/players")
public class GameController {

	@Autowired
	private IGameService gameService;

	@Autowired
	private IPlayerService playerService;

	@PostMapping("/{player_id}/games") // CREATE A NEW GAME
	public ResponseEntity<Message> playGame(@PathVariable String player_id) {
		try {
			GameDTO returnedGame = gameService.saveGame(player_id);

			return new ResponseEntity<Message>(new Message("Game created successfully!", returnedGame, ""),
					HttpStatus.CREATED);

		} catch (NullPointerException noPlayerError) { // non existent player error
			return new ResponseEntity<Message>(
					new Message("There is no player with id = " + player_id + " in the database!", null,
							noPlayerError.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Failed to create a new game!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{player_id}/games") // READ ALL GAMES OF A PLAYER
	public ResponseEntity<Message> retrievePlayerGames(@PathVariable String player_id) {
		String playerName = playerService.getPlayerByID(player_id).getName();
		List<GameDTO> playerGames = gameService.gamesByPlayer(player_id);

		// check if player has no games played yet
		if (playerGames.isEmpty()) {
			return new ResponseEntity<Message>(
					new Message("Player " + playerName + " with id " + player_id + " has not played any game yet.",
							null, ""),
					HttpStatus.OK);
		}
		return new ResponseEntity<Message>(new Message("Games retrieved successfully!", playerGames, ""),
				HttpStatus.OK);

	}

	@DeleteMapping("/{player_id}/games") // DELETE ALL GAMES OF A PLAYER
	public ResponseEntity<Message> deleteGames(@PathVariable String player_id) {
		String playerName = playerService.getPlayerByID(player_id).getName();
		List<GameDTO> playerGames = gameService.gamesByPlayer(player_id);

		// check if player has no games played yet
		if (playerGames.isEmpty()) {
			return new ResponseEntity<Message>(
					new Message("Player " + playerName + " has not played any game yet.", null, ""), HttpStatus.OK);
		}

		gameService.deleteGamesByPlayer(player_id);

		return new ResponseEntity<Message>(new Message("Deleted all games of player: " + playerName, "", ""),
				HttpStatus.OK);
	}

}
