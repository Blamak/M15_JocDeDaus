package com.ITAcademy.M15_JocDeDaus.Services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Game;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;
import com.ITAcademy.M15_JocDeDaus.Repositories.IGameRepository;

/**
 * Implementation of all the services that imply any operation with the Game
 * entity
 * 
 */
@Service
public class GameImplService implements IGameService {

	@Autowired
	private IGameRepository gameRepository;

	@Autowired
	private IPlayerService playerService;

	@Override
	public Game saveGame(long player_id) {
		Game newGame = new Game();
		PlayerDTO player = playerService.getPlayerByID(player_id);

		newGame.setPlayer(playerService.mapDtotoEntity(player));

		// roll the two dices
		int dice1 = (int) (Math.random() * 6 + 1);
		int dice2 = (int) (Math.random() * 6 + 1);

		// dice1 + dice2 = 7 is a win, other result is a loss
		String result = "";
		if ((dice1 + dice2) == 7) {
			result = "won";
		} else {
			result = "lost";
		}

		// set game attributes
		newGame.setDice1(dice1);
		newGame.setDice2(dice2);
		newGame.setResult(result);

		return gameRepository.save(newGame);
	}

	@Override
	public List<Game> gamesByPlayer(long player_id) {
		PlayerDTO playerDTO = playerService.getPlayerByID(player_id);
		Player player = playerService.mapDtotoEntity(playerDTO);
		return gameRepository.findByPlayer(player);
	}

	@Override
	public void deleteGamesByPlayer(long player_id) {
		PlayerDTO playerDTO = playerService.getPlayerByID(player_id);
		Player player = playerService.mapDtotoEntity(playerDTO);
		
		List<Game> playerGames = gameRepository.findByPlayer(player);
		for (Game game : playerGames) {
			gameRepository.delete(game);
		}
	}

	@Override
	public BigDecimal calculateWinRate(long player_id) {
		float wonGames = 0;
		float totalGames = 0;
		float wonPercent = 0;
		List<Game> playerGames = this.gamesByPlayer(player_id);

		if (playerGames.size() != 0) {
			for (Game game : playerGames) {
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
			return BigDecimal.ZERO;
		}
	}

}
