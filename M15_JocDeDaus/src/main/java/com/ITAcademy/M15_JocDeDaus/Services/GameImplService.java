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
import com.ITAcademy.M15_JocDeDaus.Entities.Player;
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
	public GameDTO saveGame(long player_id) {
		GameDTO newGameDTO = new GameDTO();
		PlayerDTO player = playerService.getPlayerByID(player_id);
		BigDecimal playerWinRate;
		

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
		
		// set game attributes, except id
		newGameDTO.setDice1(dice1);
		newGameDTO.setDice2(dice2);
		newGameDTO.setResult(result);
		newGameDTO.setPlayer(player);
		
		// add to database, after entity conversion
		Game newGame = this.mapDTOtoEntity(newGameDTO);
		gameRepository.save(newGame);
		
		// update win rate's player in database
		playerWinRate = this.calculateWinRate(player_id);
		player.setWin_rate(playerWinRate);
		playerService.replacePlayer(player);
		
		// set id to the DTO game
		newGameDTO.setGames_id(newGame.getGame_id());
		
		return newGameDTO;
	}

	@Override
	public List<GameDTO> gamesByPlayer(long player_id) {
		PlayerDTO playerDTO = playerService.getPlayerByID(player_id);
		Player player = playerService.mapDTOtoEntity(playerDTO);
		
		List<Game> gamesList = gameRepository.findByPlayer(player);
		List<GameDTO> gamesDTOList = new ArrayList<GameDTO>();

		// populate the list of games DTO after conversion from entity
		for (Game game : gamesList) {
			GameDTO gameDTO = this.mapEntitytoDTO(game);
			gamesDTOList.add(gameDTO);
		}
		
		return gamesDTOList;
	}

	@Override
	public void deleteGamesByPlayer(long player_id) {
		PlayerDTO playerDTO = playerService.getPlayerByID(player_id);
		Player player = playerService.mapDTOtoEntity(playerDTO);

		List<Game> playerGames = gameRepository.findByPlayer(player);
		for (Game game : playerGames) {
			gameRepository.delete(game);
		}
	}

	/**
	 * Calculates the percentage of games won by a particular player
	 * 
	 * @param {long} player_id
	 * @return the rate of  games won by the player
	 */
	@Override
	public BigDecimal calculateWinRate(long player_id) {
		float wonGames = 0;
		float totalGames = 0;
		float wonPercent = 0;
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
		
		PlayerDTO playerDTO = playerService.mapEntitytoDTO(game.getPlayer());
		
		gameDTO.setGames_id(game.getGame_id());
		gameDTO.setDice1(game.getDice1());
		gameDTO.setDice2(game.getDice2());
		gameDTO.setResult(game.getResult());
		gameDTO.setPlayer(playerDTO);

		return gameDTO;
	}
	
	// DTO-Entity conversion
	private Game mapDTOtoEntity(GameDTO gameDTO) {
		Game game = new Game();
		Player player = playerService.mapDTOtoEntity(gameDTO.getPlayer());
		
		game.setGames_id(gameDTO.getGame_id());
		game.setDice1(gameDTO.getDice1());
		game.setDice2(gameDTO.getDice2());
		game.setResult(gameDTO.getResult());
		game.setPlayer(player);
		
		return game;
		
		
	}
	
}
