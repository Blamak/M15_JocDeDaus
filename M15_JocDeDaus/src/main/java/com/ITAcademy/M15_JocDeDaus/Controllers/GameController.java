package com.ITAcademy.M15_JocDeDaus.Controllers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ITAcademy.M15_JocDeDaus.DTO.Message;
import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Game;
import com.ITAcademy.M15_JocDeDaus.Services.IGameService;
import com.ITAcademy.M15_JocDeDaus.Services.IPlayerService;

@RestController
@RequestMapping("/players")
public class GameController {

	@Autowired
	private IGameService gameService;

	@Autowired
	private IPlayerService playerService;

	@PostMapping("/{player_id}/games") // CREATE NEW GAME
	public ResponseEntity<Message> playGame(@PathVariable long player_id) {
		Game returnedGame = gameService.saveGame(player_id);
		System.out.println(returnedGame.getDice1());

		return new ResponseEntity<Message>(new Message("Game created successfully!", returnedGame, ""), HttpStatus.OK);

	}

	@GetMapping("/{player_id}/games") // READ GAMES OF A PLAYER
	public ResponseEntity<Message> listPlayerGames(@PathVariable long player_id) {
		List<Game> playerGames = gameService.gamesByPlayer(player_id);
		
		String playerName = playerService.getPlayerByID(player_id).getName();

		return new ResponseEntity<Message>(new Message("List of games successfully retrieved of player: " + playerName, playerGames, ""),
				HttpStatus.OK);

	}

	@DeleteMapping("/{player_id}/games")
	public ResponseEntity<Message> deleteGames(@PathVariable long player_id) {
		String playerName = playerService.getPlayerByID(player_id).getName();
		gameService.deleteGamesByPlayer(player_id);
		return new ResponseEntity<Message>(new Message("Deleted all games of player: " + playerName, "", ""), HttpStatus.OK);
	}

	@GetMapping("/ranking") // List of Players by winning percentage, descending order
	public ResponseEntity<Message> ranking() {
		try {
			LinkedHashMap<String, BigDecimal> playersSortedByRank = this.getPlayersByRank();

			return new ResponseEntity<Message>(new Message("Players ranking successfully retrieved!",playersSortedByRank, ""), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Failed to get ranking !", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/ranking/loser")
	public ResponseEntity<?> getLoser() {
		LinkedHashMap<String, BigDecimal> playersSortedByRank = this.getPlayersByRank();
		// Get all keys from the LinkedHashMap and convert it to an array
		String[] arrayKeys = playersSortedByRank.keySet().toArray(new String[playersSortedByRank.size()]);

		String loserName = arrayKeys[arrayKeys.length - 1];

		return ResponseEntity.ok(loserName);
	}

	@GetMapping("/ranking/winner")
	public ResponseEntity<?> getWinner() {
		LinkedHashMap<String, BigDecimal> playersSortedByRank = this.getPlayersByRank();
		// Get all keys from the LinkedHashMap and convert it to an array
		String[] arrayKeys = playersSortedByRank.keySet().toArray(new String[playersSortedByRank.size()]);

		String loserName = arrayKeys[0];

		return ResponseEntity.ok(loserName);
	}

	private LinkedHashMap<String, BigDecimal> getPlayersByRank() {
		List<PlayerDTO> allPlayers = playerService.getAllPlayers();
		Map<String, BigDecimal> players_winRate = new HashMap<>();

		for (PlayerDTO player : allPlayers) {
			BigDecimal winRate = gameService.calculateWinRate(player.getPlayer_id());
			String name = player.getName();
			players_winRate.put(name, winRate);
		}

		// sort the hashmap
		LinkedHashMap<String, BigDecimal> reverseSortedMap = new LinkedHashMap<>();
		players_winRate.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

		return reverseSortedMap;
	}

}
