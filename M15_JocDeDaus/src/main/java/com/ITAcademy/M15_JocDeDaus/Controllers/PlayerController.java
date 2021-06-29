package com.ITAcademy.M15_JocDeDaus.Controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils.Message;
import com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils.PlayerRepresentation;
import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Exceptions.PlayerNotFoundException;
import com.ITAcademy.M15_JocDeDaus.Services.IPlayerService;

@RestController
@RequestMapping("/players")
public class PlayerController {

	@Autowired
	private IPlayerService playerService;

	@PostMapping("") // CREATE NEW PLAYER
	public ResponseEntity<Message> addPlayer(@RequestBody Map<String, String> nameMap) {
		try {
			String name = nameMap.get("name");
			PlayerDTO newPlayer = new PlayerDTO();

			if (name == null || name.equals("")) {
				newPlayer.setName("ANÒNIM");
			} else {
				newPlayer.setName(name);
			}
			// Check if name exists - if not, save player to database
			if (playerService.checkNameDuplicated(name)) {
				return new ResponseEntity<Message>(new Message("Another player already has this name!", null, null),
						HttpStatus.CONFLICT);
			} else {
				PlayerDTO playerReturned = playerService.savePlayer(newPlayer);
				return new ResponseEntity<Message>(new Message("Player created successfully!", new PlayerRepresentation(playerReturned), ""),
						HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("New Player post failed!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{player_id}")
	public ResponseEntity<PlayerRepresentation> retrievePlayer(@PathVariable final long player_id) {
		PlayerDTO player = playerService.getPlayerByID(player_id);

		return ResponseEntity.ok(new PlayerRepresentation(player));
	}

	@GetMapping("")
	public CollectionModel<PlayerRepresentation> retrieveAllPlayers() {
		List<PlayerDTO> allPlayers = playerService.getAllPlayers();
		List<PlayerRepresentation> returnedList = new ArrayList<PlayerRepresentation>();
		// conversion from playerDTO to PlayerRepresentation
		for (PlayerDTO playerDTO : allPlayers) {
			PlayerRepresentation playerRep = new PlayerRepresentation(playerDTO);
			returnedList.add(playerRep);
		}
		// throw exception if database has no players
		if (allPlayers == null || allPlayers.size() == 0) {
			throw new PlayerNotFoundException("There are no players registered yet.");
		}

		// HATEOAS implementation
		Link selfLink = linkTo(PlayerController.class).withSelfRel();
		Link rankingLink = linkTo(methodOn(RankingController.class).getRankingList()).withRel("ranking");
		Link bestPlayerLink = linkTo(methodOn(RankingController.class).getWinner()).withRel("best-player");
		Link worstPlayerLink = linkTo(methodOn(RankingController.class).getLoser()).withRel("worst-player");
		CollectionModel<PlayerRepresentation> result = CollectionModel.of(returnedList, selfLink, rankingLink, bestPlayerLink, worstPlayerLink);
		
		return result;
	}

	@PutMapping("/{player_id}") // UPDATE PLAYER'S NAME
	public ResponseEntity<Message> updatePlayersName(@RequestBody Map<String, String> nameMap, @PathVariable long player_id) {
		try {
			PlayerDTO playerReturned = playerService.getPlayerByID(player_id);

			String newName = nameMap.get("name");
			
			// check for no change or duplicate name conflict
			if (newName.equals(playerReturned.getName())) {
				return new ResponseEntity<Message>(new Message("", null, null),
						HttpStatus.NOT_MODIFIED);
			} else if (playerService.checkNameDuplicated(newName)) {
				return new ResponseEntity<Message>(new Message("Another player already has this name!", null, "Constraint Violation"),
						HttpStatus.CONFLICT);
			}
			
			if (newName == null || newName.equals("")) {
				playerReturned.setName("ANÒNIM");
			} else {
				playerReturned.setName(newName);
			}
			
			playerService.replacePlayer(playerReturned);
			return new ResponseEntity<Message>(new Message("Player name successfully updated", playerReturned, ""),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Failed update Player!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
