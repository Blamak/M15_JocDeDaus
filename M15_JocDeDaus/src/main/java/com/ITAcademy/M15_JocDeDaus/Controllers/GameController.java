package com.ITAcademy.M15_JocDeDaus.Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	public ResponseEntity<Message> playGame(@PathVariable String player_id) {
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
	public CollectionModel<GameDTO> retrievePlayerGames(@PathVariable String player_id) {
//		try {
		String playerName = playerService.getPlayerByID(player_id).getName();
		List<GameDTO> playerGames = gameService.gamesByPlayer(player_id);

//			Link link = linkTo(GameController.class).withSelfRel();

		CollectionModel<GameDTO> result = CollectionModel.of(playerGames);

		return result;
//			
////			for (GameDTO game : playerGames) {
////				Link selfLink = linkTo(game).
////			}
//			
//			
////			// player with or without games
////			if (playerGames.isEmpty()) {
////				return new ResponseEntity<Message>(
////						new Message("Player " + playerName + " has not played any game yet.", null, ""), HttpStatus.OK);
////			} else {
////				return new ResponseEntity<Message>(
////						new Message("List of games successfully retrieved of player: " + playerName, playerGames, ""),
////						HttpStatus.OK);
////			}
////
////		} catch (NullPointerException noPlayerError) { // non existent player error
////			return new ResponseEntity<Message>(
////					new Message("There is no player with id = " + player_id + " in the database!", null,
////							noPlayerError.getMessage()),
////					HttpStatus.BAD_REQUEST);
////		} catch (Exception e) {
////			return new ResponseEntity<Message>(new Message("Failed to create a new game!", null, e.getMessage()),
////					HttpStatus.INTERNAL_SERVER_ERROR);
////		}
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

//	// ======================= REQUESTS INVOLVING RANKING ======================== // 
//
//	/**
//	 * This method is called in the below GET requests: "/ranking", "/ranking/loser"
//	 * and "/ranking/winner"
//	 * 
//	 * Creates a map of players ordered by their winning rate.
//	 * 
//	 * Through a loop adds every player's name and his rate to a HashMap, which is
//	 * converted into a LinkedHashMap to sort the players by rate and remove the
//	 * players without games.
//	 * 
//	 * @return a LinkedHashMap with key-value pairs: player name - win rate,
//	 *         omitting the players with no games.
//	 */
//	private List<String> getPlayersByRank() {
//		// retrieve and store all players' list
//		List<PlayerDTO> allPlayers = playerService.getAllPlayers();
//		
//		
//		// Synchronized players array with rates map (this way allowing repeated player names - case "ANÒNIM")
//		List<String> playersNamesArray = new ArrayList<>();
////		List<BigDecimal> winRatesArray = null;
//
//		
//		Map<Integer, BigDecimal> ratesWithIndex = new HashMap<>();
//		
//		int index = 0;
//		for (PlayerDTO player : allPlayers) {
//			String name = player.getName();
//			BigDecimal winRate = gameService.calculateWinRate(player.getId());
//			playersNamesArray.add(name);
//			ratesWithIndex.put(index, winRate);
//			index++;
//		}
//
//		
//		
//		// sort the map - descending order
//		LinkedHashMap<Integer, BigDecimal> ratesWithIndexSorted = ratesWithIndex.entrySet().stream()
////				.filter(map -> !BigDecimal.ZERO.equals(map.getValue()))
//				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//				.collect(Collectors.toMap(
//						Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//		
//		List<String> playersWithGamesSorted = new ArrayList<>();
//		
//		Set<Integer> keys = ratesWithIndexSorted.keySet();
//		for (Integer key : keys) {
//			String name = playersNamesArray.get(key);
//			BigDecimal winRate = ratesWithIndexSorted.get(key);
//			String listContent = name + " - " + winRate;
//			playersWithGamesSorted.add(listContent);
//			
//		}
//		
//		return playersWithGamesSorted;
//	}
//	

	private CustomMap_NamesRates getPlayersByRank() {
		// retrieve and store all players' list
		List<PlayerDTO> allPlayers = playerService.getAllPlayers();
		CustomMap_NamesRates namesAndRates = new CustomMap_NamesRates();

		for (PlayerDTO player : allPlayers) {
			String name = player.getName();
			BigDecimal winRate = gameService.calculateWinRate(player.getId());
			namesAndRates.addNameAndRate(name, winRate);
		}

		return namesAndRates;
	}

	@GetMapping("/ranking") // LIST OF PLAYERS BY WINNING RATE, DESCENDING ORDER
	public ResponseEntity<Message> getRanking() {
		// get the map of sorted by rate players
		CustomMap_NamesRates playersSortedByRank = this.getPlayersByRank();

		if (playersSortedByRank.getPlayers() == 0) { // no games case
			return new ResponseEntity<Message>(new Message("Nobody has played any game yet.", null, ""), HttpStatus.OK);
		} else {
			return new ResponseEntity<Message>(
					new Message("Players ranking successfully retrieved!", playersSortedByRank.showCustomMap(), ""),
					HttpStatus.OK);
		}

	}

	@GetMapping("/ranking/loser") // WORST PLAYER
	public ResponseEntity<Message> getLoser() {
		try {
			// get the map of sorted by rate players
			CustomMap_NamesRates playersSortedByRank = this.getPlayersByRank();
			CustomMap_NamesRates worstPlayers = new CustomMap_NamesRates();

			// synchronized lists names and rates
			List<BigDecimal> winRates = playersSortedByRank.getWinRates();
			List<String> names = playersSortedByRank.getNames();

			int lastIndex = winRates.size() - 1;
			String loserName = "";
			BigDecimal loserRate = null;
			do {
				loserRate = winRates.get(lastIndex);
				loserName = names.get(lastIndex);
				worstPlayers.addNameAndRate(loserName, loserRate);
				lastIndex--;
			} while (loserRate.compareTo(winRates.get(lastIndex)) == 0);

			return new ResponseEntity<Message>(
					new Message("Worst player successfully retrieved!", worstPlayers.showCustomMap(), ""),
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
			CustomMap_NamesRates playersSortedByRank = this.getPlayersByRank();
			CustomMap_NamesRates bestPlayers = new CustomMap_NamesRates();

			// synchronized lists names and rates
			List<BigDecimal> winRates = playersSortedByRank.getWinRates();
			List<String> names = playersSortedByRank.getNames();

			int firstIndex = 0;
			String winnerName = "";
			BigDecimal winnerRate = null;
			do {
				winnerRate = winRates.get(firstIndex);
				winnerName = names.get(firstIndex);
				bestPlayers.addNameAndRate(winnerName, winnerRate);
				firstIndex++;
			} while (winnerRate.compareTo(winRates.get(firstIndex)) == 0);

			return new ResponseEntity<Message>(
					new Message("Best player successfully retrieved!", bestPlayers.showCustomMap(), ""), HttpStatus.OK);

		} catch (ArrayIndexOutOfBoundsException noGamesException) { // no games error handling
			return new ResponseEntity<Message>(new Message("No games played yet!", null, noGamesException.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Request failed!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
