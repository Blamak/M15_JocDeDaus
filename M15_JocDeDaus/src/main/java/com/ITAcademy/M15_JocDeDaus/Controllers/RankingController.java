package com.ITAcademy.M15_JocDeDaus.Controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils.CustomMap_NamesRates;
import com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils.Message;
import com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils.PlayerRepresentation;
import com.ITAcademy.M15_JocDeDaus.Services.IGameService;
import com.ITAcademy.M15_JocDeDaus.Services.IPlayerService;

@RestController
@RequestMapping("/players")
public class RankingController {

	@Autowired
	private IGameService gameService;
	@Autowired
	private IPlayerService playerService;

	@GetMapping("/ranking") // LIST OF PLAYERS BY WINNING RATE, DESCENDING ORDER
	public ResponseEntity<Message> getRankingList() {
		if (gameService.checkNoGames()) {
			return new ResponseEntity<Message>(new Message("Nobody has played any game yet.", null, ""), HttpStatus.OK);
		}
		// get the map of all players sorted by win rate 
		CustomMap_NamesRates playersSortedByRank = this.getPlayersByRank();

		return new ResponseEntity<Message>(
				new Message("Players ranking successfully retrieved!", playersSortedByRank.showCustomMap(), ""),
				HttpStatus.OK);

	}

	@GetMapping("/ranking/loser") // WORST PLAYER
	public ResponseEntity<Message> getLoser() {
		try {
			if (gameService.checkNoGames()) {
				return new ResponseEntity<Message>(new Message("Nobody has played any game yet.", null, ""),
						HttpStatus.OK);
			}
			
			CustomMap_NamesRates worstPlayers = this.getWorstOrBest("L");
			return new ResponseEntity<Message>(
					new Message("Worst player(s) successfully retrieved!", worstPlayers.showCustomMap(), ""),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Request failed!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/ranking/winner") // BEST PLAYER
	public ResponseEntity<Message> getWinner() {
		try {
			if (gameService.checkNoGames()) {
				return new ResponseEntity<Message>(new Message("Nobody has played any game yet.", null, ""),
						HttpStatus.OK);
			}
			
			CustomMap_NamesRates bestPlayers = this.getWorstOrBest("W");
			return new ResponseEntity<Message>(
					new Message("Best player(s) successfully retrieved!", bestPlayers.showCustomMap(), ""), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Request failed!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * Creates a customized list of maps, each map with key-value name-win rate.
	 * 
	 */
	private CustomMap_NamesRates getPlayersByRank() {
		// retrieve and store all players' list
		List<PlayerRepresentation> allPlayers = playerService.getAllPlayers();
		CustomMap_NamesRates namesAndRates = new CustomMap_NamesRates();

		for (PlayerRepresentation player : allPlayers) {
			String name = player.getName();
			BigDecimal winRate = gameService.calculateWinRate(player.getPlayer_id());
			namesAndRates.addNameAndRate(name, winRate);
		}
		return namesAndRates;
	}

	private CustomMap_NamesRates getWorstOrBest(String letter) {
		// get the map of sorted by rate players
		CustomMap_NamesRates playersSortedByRank = this.getPlayersByRank();
		CustomMap_NamesRates bestOrWorstPlayers = new CustomMap_NamesRates();

		// synchronized lists of names and rates
		List<BigDecimal> winRates = playersSortedByRank.getWinRates();
		List<String> names = playersSortedByRank.getNames();

		int index;
		int factor;
		if (letter.equals("W")) {
			index = 0;
			factor = 1;
		} else {
			index = winRates.size() - 1;
			factor = -1;
		}
		
		String playerName = "";
		BigDecimal playerRate = null;
		do {
			playerRate = winRates.get(index);
			playerName = names.get(index);
			bestOrWorstPlayers.addNameAndRate(playerName, playerRate);
			index += factor;
		} while (playerRate.compareTo(winRates.get(index)) == 0);

		return bestOrWorstPlayers;
	}
}
