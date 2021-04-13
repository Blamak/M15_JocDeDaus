package com.ITAcademy.M15_JocDeDaus.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;
import com.ITAcademy.M15_JocDeDaus.Repositories.IPlayerRepository;

@Service
public class PlayerImplService implements IPlayerService {

	@Autowired
	IPlayerRepository playerRepository;

	@Override
	public PlayerDTO savePlayer(PlayerDTO playerDTO) {
		Player player = this.mapDTOtoEntity(playerDTO);
		playerRepository.save(player);
		return this.mapEntitytoDTO(player);
	}

	@Override
	public List<PlayerDTO> getAllPlayers() {
		List<PlayerDTO> playersDTOList = new ArrayList<PlayerDTO>();
		List<Player> playersList = playerRepository.findAll();

		if (playersList != null && playersList.size() > 0) {
			// after conversion from every player entity, populate the list of players DTO
			for (Player player : playersList) {
				playersDTOList.add(this.mapEntitytoDTO(player));
			}
		}

		return playersDTOList;
	}

	@Override
	public PlayerDTO replacePlayerName(PlayerDTO playerDTO) {
		Player player = this.mapDTOtoEntity(playerDTO);
		
		playerRepository.save(player);
		
		return this.mapEntitytoDTO(player);
	}

	@Override
	public PlayerDTO getPlayerByID(long player_id) {
		Optional<Player> player = playerRepository.findById(player_id);
		
		if (player.isPresent()) {
			return this.mapEntitytoDTO(player.get());

		} else {
			return null;
		}
	}

	@Override
	public boolean checkPlayerExists(long player_id) {
		if (playerRepository.existsById(player_id)) {
			return true;
		}
		
		return false;
	}

	// DTO-Entity conversion
	@Override
	public Player mapDTOtoEntity(PlayerDTO playerDTO) {
		Player player = new Player();

		if (playerDTO.getPlayer_id() != null) {
			player.setPlayer_id(playerDTO.getPlayer_id());
		}

		player.setName(playerDTO.getName());
		player.setDate_registered(playerDTO.getDate_registered());

		return player;
	}

	// Entity-DTO conversion
	@Override
	public PlayerDTO mapEntitytoDTO(Player player) {
		PlayerDTO playerDTO = new PlayerDTO();

		playerDTO.setPlayer_id(player.getPlayer_id());
		playerDTO.setName(player.getName());
		playerDTO.setDate_registered(player.getDate_registered());

		return playerDTO;
	}

}
