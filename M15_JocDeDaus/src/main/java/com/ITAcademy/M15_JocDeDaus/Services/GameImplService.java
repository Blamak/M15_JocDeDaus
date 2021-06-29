package com.ITAcademy.M15_JocDeDaus.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ITAcademy.M15_JocDeDaus.DTO.GameDTO;
import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Game;
import com.ITAcademy.M15_JocDeDaus.Repositories.IGameRepository;

/**
 * Implementation of all the services that imply any operation 
 * with the Game entity.
 * 
 */
@Service
public class GameImplService implements IGameService {

	private final IGameRepository gameRepository;
	// Repository injected via constructor injection, ensuring a consistent state
	GameImplService(IGameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	@Autowired
	private IPlayerService playerService;

	@Override
	public GameDTO playGame(String player_id) {
		// roll the two dices
		int dice1 = (int) (Math.random() * 6 + 1);
		int dice2 = (int) (Math.random() * 6 + 1);
		// dice1 + dice2 = 7 is a win, any other result is a defeat
		String result = "";
		if ((dice1 + dice2) == 7) {
			result = "won";
			
		} else {
			result = "lost";
		}
		
		GameDTO newGameDTO = new GameDTO();
		PlayerDTO player = playerService.getPlayerByID(player_id);
		BigDecimal playerWinRate;
		
		// set game attributes, except id
		newGameDTO.setDice1(dice1);
		newGameDTO.setDice2(dice2);
		newGameDTO.setResult(result);
		newGameDTO.setPlayerId(player_id);

		Game newGame = this.mapDTOtoEntity(newGameDTO);
		gameRepository.save(newGame);

		// update win rate's player in database
		playerWinRate = this.calculateWinRate(player_id);
		player.setWinRate(playerWinRate);
		playerService.replacePlayer(player);
		
		// set id to the DTO game
		newGameDTO.setId(newGame.getId());
		return newGameDTO;
	}

	@Override
	public List<GameDTO> gamesByPlayer(String player_id) {
		List<Game> gamesList = gameRepository.findByPlayerId(player_id);
		List<GameDTO> gamesDTOList = new ArrayList<GameDTO>();

		// populate the list of games DTO after conversion from entity
		for (Game game : gamesList) {
			GameDTO gameDTO = this.mapEntitytoDTO(game);
			gamesDTOList.add(gameDTO);
		}
		return gamesDTOList;
	}

	@Override
	public void deleteGamesByPlayer(String player_id) {
		PlayerDTO playerDTO = playerService.getPlayerByID(player_id);
		playerDTO.setWinRate(new BigDecimal("0.0"));
		playerService.replacePlayer(playerDTO);
		List<Game> playerGames = gameRepository.findByPlayerId(player_id);
		for (Game game : playerGames) {
			gameRepository.delete(game);
		}
	}
	
	@Override
	public Boolean checkNoGames() {
		return gameRepository.findAll().isEmpty();
	}

	/**
	 * Calculates the percentage of games won by a particular player
	 * 
	 * @param {String} player_id
	 * @return the rate of games won by the player
	 */
	@Override
	public BigDecimal calculateWinRate(String player_id) {
		Double wonGames = 0.0;
		Double totalGames = 0.0;
		Double wonPercent = 0.0;
		List<GameDTO> playerGames = this.gamesByPlayer(player_id);
		
		if (playerGames.size() != 0) {
			// count the number of games played and games won of the player
			for (GameDTO game : playerGames) {
				totalGames += 1;
				if (game.getResult().equals("won")) {
					wonGames += 1;
				}
			}
			wonPercent = (wonGames / totalGames) * 100;
			// round to 2 decimal points
			BigDecimal rounded_wonPercent = new BigDecimal(wonPercent).setScale(2, RoundingMode.HALF_UP);
			return rounded_wonPercent;
		} else {
			// return Zero if the player has no games
			return BigDecimal.ZERO;
		}
	}

	// Entity-DTO conversion
	private GameDTO mapEntitytoDTO(Game game) {
		GameDTO gameDTO = new GameDTO();
		String player_id = game.getPlayerId();
		gameDTO.setId(game.getId());
		gameDTO.setDice1(game.getDice1());
		gameDTO.setDice2(game.getDice2());
		gameDTO.setResult(game.getResult());
		gameDTO.setPlayerId(player_id);

		return gameDTO;
	}
	
	// DTO-Entity conversion
	private Game mapDTOtoEntity(GameDTO gameDTO) {
		Game game = new Game();
		String player_id = gameDTO.getPlayerId();
		game.setId(gameDTO.getId());
		game.setDice1(gameDTO.getDice1());
		game.setDice2(gameDTO.getDice2());
		game.setResult(gameDTO.getResult());
		game.setPlayerId(player_id);
		
		return game;
	}
	
}
