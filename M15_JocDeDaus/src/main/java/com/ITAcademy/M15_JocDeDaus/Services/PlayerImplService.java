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

	@Override
	public List<PlayerDTO> getAllPlayers() {
		List<PlayerDTO> playersDTOList = new ArrayList<PlayerDTO>();
		List<Player> playersList = (List<Player>) playerRepository.findAll();

		if (playersList != null && playersList.size() > 0) {
			// after conversion from every player entity, populate the list of DTO players
			for (Player player : playersList) {
				playersDTOList.add(this.mapEntitytoDTO(player));
			}
		}

		return playersDTOList;
	}

	@Override
	public void replacePlayer(PlayerDTO playerDTO) {
		Player player = this.mapDTOtoEntity(playerDTO);
		playerRepository.save(player);

	}

	@Override
	public PlayerDTO getPlayerByID(String player_id) {
		PlayerDTO playerReturned = playerRepository.findById(player_id).map(player -> this.mapEntitytoDTO(player))
				.orElseThrow(() -> new PlayerNotFoundException("not found Player with id " + player_id));

		if (playerReturned.getName() == null) {
			playerReturned.setName("ANÒNIM");
		}

		return playerReturned;
	}

	@Override
	public boolean checkNameDuplicated(String name) {

		if (name.equals("ANÒNIM") || !playerRepository.existsByName(name)) {
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
		player.setWinRate(playerDTO.getWinRate());
		player.setDate_registered(playerDTO.getDate_registered());

		return player;
	}

	// Entity-DTO conversion
	@Override
	public PlayerDTO mapEntitytoDTO(Player player) {
		PlayerDTO playerDTO = new PlayerDTO();

		playerDTO.setId(player.getId());
		playerDTO.setName(player.getName());
		playerDTO.setWinRate(player.getWinRate());
		playerDTO.setDate_registered(player.getDate_registered());

		return playerDTO;
	}

}
