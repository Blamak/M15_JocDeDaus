package com.ITAcademy.M15_JocDeDaus.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;
import com.ITAcademy.M15_JocDeDaus.Repositories.PlayerRepository;

@Service
public class PlayerImplService implements IPlayerService {

	@Autowired
	PlayerRepository playerRepository;

	@Override
	public ResponseEntity<PlayerDTO> savePlayer(PlayerDTO playerDTO) {

		Player player = this.mapDtotoEntity(playerDTO);
		player = playerRepository.save(player);

		return new ResponseEntity<PlayerDTO>(playerDTO, HttpStatus.CREATED);
	}

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
