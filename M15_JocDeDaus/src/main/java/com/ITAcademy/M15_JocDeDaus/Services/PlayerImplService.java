package com.ITAcademy.M15_JocDeDaus.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;
import com.ITAcademy.M15_JocDeDaus.Repositories.PlayerRepository;

@Service
public class PlayerImplService implements IPlayerService {

	@Autowired
	PlayerRepository playerRepository;

	// POST:/players
	@Override
	public PlayerDTO savePlayer(PlayerDTO playerDTO) {
		Player player = this.mapDtotoEntity(playerDTO);
		playerRepository.save(player);
		return this.mapEntitytoDTO(player);
		
	}

	// GET:/players
	@Override
	public List<PlayerDTO> getAllPlayers() {
		List<PlayerDTO> result = null;
		List<Player> players = playerRepository.findAll();
		
		if (players != null && players.size() > 0) {
			result = new ArrayList<PlayerDTO>();
			for (Player player : players) {
				result.add(this.mapEntitytoDTO(player));
			}
		}

		return result;
	}
	
	// PUT:/players
	@Override
	public PlayerDTO replacePlayerName(PlayerDTO playerDTO) {
		Player player = this.mapDtotoEntity(playerDTO);
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
		if(playerRepository.existsById(player_id)) {
			return true;
		}
		return false;
	}
	
	// DTO-entity conversion
	private Player mapDtotoEntity(PlayerDTO dto) {

		Player player = new Player();

		if (dto.getPlayer_id() != null) {
			player.setPlayer_id(dto.getPlayer_id());
		}

		player.setName(dto.getName());
		player.setCurrentTime(dto.getCurrentTime());

		return player;
	}

	// Entity-DTO conversion
	private PlayerDTO mapEntitytoDTO(Player entity) {

		PlayerDTO dto = new PlayerDTO();

		dto.setPlayer_id(entity.getPlayer_id());
		dto.setName(entity.getName());
		dto.setCurrentTime(entity.getCurrentTime());

		return dto;
	}


}
