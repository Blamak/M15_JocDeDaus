package com.ITAcademy.M15_JocDeDaus.Controllers;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ITAcademy.M15_JocDeDaus.DTO.GameDTO;
import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Response.Message;
import com.ITAcademy.M15_JocDeDaus.Services.IGameService;
import com.ITAcademy.M15_JocDeDaus.Services.IPlayerService;

@RestController
@RequestMapping("/players")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class GameController {

	@Autowired
	private IGameService gameService;

	@Autowired
	private IPlayerService playerService;

	@PostMapping("/{player_id}/games") // CREATE A NEW GAME
	public ResponseEntity<Message> playGame(@PathVariable long player_id) {
		try {
			GameDTO returnedGame = gameService.saveGame(player_id);

			return new ResponseEntity<Message>(new Message("Game created successfully!", returnedGame, ""),
					HttpStatus.OK);
			
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

	@GetMapping(value = "/{player_id}/games", produces = { "application/hal+json" }) // READ ALL GAMES OF A PLAYER
	public CollectionModel<GameDTO> retrievePlayerGames(@PathVariable long player_id) {
//		try {
			String playerName = playerService.getPlayerByID(player_id).getName();
			List<GameDTO> playerGames = gameService.gamesByPlayer(player_id);
			
//			Link link = linkTo(GameController.class).withSelfRel();
			
			CollectionModel<GameDTO> result = CollectionModel.of(playerGames);
			
			return result;
			
//			for (GameDTO game : playerGames) {
//				Link selfLink = linkTo(game).
//			}
			
			
//			// player with or without games
//			if (playerGames.isEmpty()) {
//				return new ResponseEntity<Message>(
//						new Message("Player " + playerName + " has not played any game yet.", null, ""), HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Message>(
//						new Message("List of games successfully retrieved of player: " + playerName, playerGames, ""),
//						HttpStatus.OK);
//			}
//
//		} catch (NullPointerException noPlayerError) { // non existent player error
//			return new ResponseEntity<Message>(
//					new Message("There is no player with id = " + player_id + " in the database!", null,
//							noPlayerError.getMessage()),
//					HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			return new ResponseEntity<Message>(new Message("Failed to create a new game!", null, e.getMessage()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}

	@DeleteMapping("/{player_id}/games") // DELETE ALL GAMES OF A PLAYER
	public ResponseEntity<Message> deleteGames(@PathVariable long player_id) {
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
	

	// ======================= REQUESTS INVOLVING RANKING ======================== // 

	/**
	 * This method is called in the below GET requests: "/ranking", "/ranking/loser"
	 * and "/ranking/winner"
	 * 
	 * Creates a map of players ordered by their winning rate.
	 * 
	 * Through a loop adds every player's name and his rate to a HashMap, which is
	 * converted into a LinkedHashMap to sort the players by rate and remove the
	 * players without games.
	 * 
	 * @return a LinkedHashMap with key-value pairs: player name - win rate,
	 *         omitting the players with no games.
	 */
	private LinkedHashMap<String, BigDecimal> getPlayersByRank() {
		// retrieve and store all players' list
		List<PlayerDTO> allPlayers = playerService.getAllPlayers();

		// new map to store the key-value pair name-rate. Populated through a loop.
		Map<String, BigDecimal> players_winRate = new HashMap<>();
		for (PlayerDTO player : allPlayers) {
			BigDecimal winRate = gameService.calculateWinRate(player.getPlayer_id());
			String name = player.getName();
			players_winRate.put(name, winRate);
		}

		// sort the map - descending order
		LinkedHashMap<String, BigDecimal> playersWithGamesSorted = players_winRate.entrySet().stream()
//				.filter(map -> !BigDecimal.ZERO.equals(map.getValue()))
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(
						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

		return playersWithGamesSorted;
	}

	@GetMapping("/ranking") // LIST OF PLAYERS BY WINNING RATE, DESCENDING ORDER
	public ResponseEntity<Message> getRanking() {
		// get the map of sorted by rate players
		LinkedHashMap<String, BigDecimal> playersSortedByRank = this.getPlayersByRank();

		if (playersSortedByRank.isEmpty()) { // no games case
			return new ResponseEntity<Message>(new Message("Nobody has played any game yet.", null, ""), HttpStatus.OK);
		} else {
			return new ResponseEntity<Message>(
					new Message("Players ranking successfully retrieved!",
							playersSortedByRank, ""),
					HttpStatus.OK);
		}

	}

	@GetMapping("/ranking/loser") // WORST PLAYER
	public ResponseEntity<Message> getLoser() {
		try {
			// get the map of sorted by rate players
			LinkedHashMap<String, BigDecimal> playersSortedByRank = this.getPlayersByRank();

			// get all keys from the LinkedHashMap and store into an array
			String[] arrayKeys = playersSortedByRank.keySet().toArray(new String[playersSortedByRank.size()]);

			// retrieve the name of the worst player (or players) and his win rate
			int index = arrayKeys.length - 1;
			String result = "";
			String loserName = "";
			BigDecimal loserRate = null;
			do {
				loserName = arrayKeys[index];
				loserRate = playersSortedByRank.get(loserName);
				result += loserName + "-" + loserRate + "%" + "  ";
				index -= 1;
			} while (playersSortedByRank.values().toArray()[index].equals(loserRate));
			


			return new ResponseEntity<Message>(new Message("Worst player successfully retrieved!", result, ""),
					HttpStatus.OK);

		} catch (ArrayIndexOutOfBoundsException noGamesException) { // no games error handling
			return new ResponseEntity<Message>(new Message("No games played yet!", null, noGamesException.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Request failed!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/ranking/winner") // BEST PLAYER
	public ResponseEntity<Message> getWinner() {
		try {
			// get the map of sorted by rate players
			LinkedHashMap<String, BigDecimal> playersSortedByRank = this.getPlayersByRank();

			// get all keys from the LinkedHashMap and convert to an array
			String[] arrayKeys = playersSortedByRank.keySet().toArray(new String[playersSortedByRank.size()]);

			// retrieve the name of the best player (or players) and his rate
			int index = 0;
			String result = "";
			String winnerName = "";
			BigDecimal winnerRate = null;
			do {
				winnerName = arrayKeys[index];
				winnerRate = playersSortedByRank.get(winnerName);
				result += winnerName + "-" + winnerRate + "%" + "  ";
				index += 1;
			} while (playersSortedByRank.values().toArray()[index].equals(winnerRate));


			return new ResponseEntity<Message>(new Message("Best player successfully retrieved!", result, ""),
					HttpStatus.OK);
			
		} catch (ArrayIndexOutOfBoundsException noGamesException) { // no games error handling
			return new ResponseEntity<Message>(new Message("No games played yet!", null, noGamesException.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Request failed!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
