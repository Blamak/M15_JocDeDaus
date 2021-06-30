package com.ITAcademy.M15_JocDeDaus.Services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;
import com.ITAcademy.M15_JocDeDaus.Exceptions.PlayerNotFoundException;
import com.ITAcademy.M15_JocDeDaus.Repositories.IPlayerRepository;

@Service
public class PlayerImplService implements IPlayerService {

	private final IPlayerRepository playerRepository;

	// Repository injected via constructor injection, ensuring a consistent state
	PlayerImplService(IPlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Override
	public PlayerDTO savePlayer(PlayerDTO playerDTO) {
		Player savedPlayer = this.mapDTOtoEntity(playerDTO);
		playerRepository.save(savedPlayer);
		return this.mapEntitytoDTO(savedPlayer);
	}

	/**
	 * 
	 * Returns a list of all Player entities under the form of PlayerRepresentation objects
	 */
	@Override
	public List<PlayerDTO> getAllPlayers() {
		List<PlayerDTO> returnedList = new ArrayList<PlayerDTO>();
		List<Player> allPlayersList = playerRepository.findAll();

		if (allPlayersList != null && allPlayersList.size() > 0) {
			for (Player player : allPlayersList) {
				PlayerDTO playerDTO = this.mapEntitytoDTO(player);
//				PlayerRepresentation playerReturned = new PlayerRepresentation(playerDTO);  
				returnedList.add(playerDTO);
			}
		}
		return returnedList;
	}

	@Override
	public PlayerDTO replacePlayer(PlayerDTO playerDTO) {
		Player player = this.mapDTOtoEntity(playerDTO);
		playerRepository.save(player);
		return this.mapEntitytoDTO(player);
	}

	@Override
	public PlayerDTO getPlayerByID(long player_id) {
		PlayerDTO playerReturned = playerRepository
				.findById(player_id).map(player -> this.mapEntitytoDTO(player))
				.orElseThrow(() -> new PlayerNotFoundException("not found Player with id " + player_id));

		return playerReturned;
	}
	
	@Override
	public boolean checkNameDuplicated(String name) {
		if (playerRepository.findByName(name) == null || name.equals("ANÒNIM")) {
			return false;
		} else {
			return true;
		}
	}

	// DTO-Entity conversion
	@Override
	public Player mapDTOtoEntity(PlayerDTO playerDTO) {
		Player player = new Player();

		if (playerDTO.getId() != null) {
			player.setId(playerDTO.getId());
		}
		player.setName(playerDTO.getName());
		player.setWinRate(playerDTO.getWin_rate());
		player.setDate_registered(playerDTO.getDate_registered());

		return player;
	}

	// Entity-DTO conversion
	@Override
	public PlayerDTO mapEntitytoDTO(Player player) {
		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setPlayer_id(player.getId());
		playerDTO.setName(player.getName());
		playerDTO.setWin_rate(player.getWinRate());
		playerDTO.setDate_registered(player.getDate_registered());

		return playerDTO;
	}

}
