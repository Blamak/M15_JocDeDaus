package com.ITAcademy.M15_JocDeDaus.Controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import java.util.List;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils.Message;
import com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils.PlayerRepresentation;
import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Services.IPlayerService;

@RestController
@RequestMapping("/players")
public class PlayerController {

	@Autowired
	private IPlayerService playerService;

	@GetMapping("/{player_id}")
	public ResponseEntity<PlayerRepresentation> retrievePlayer(@PathVariable final String player_id) {
		PlayerDTO player = playerService.getPlayerByID(player_id);

		return ResponseEntity.ok(new PlayerRepresentation(player));
	}

//
	@GetMapping("")
	public CollectionModel<PlayerDTO> retrieveAllPlayers() {
		List<PlayerDTO> allPlayers = playerService.getAllPlayers();

		for (PlayerDTO player : allPlayers) {
			if (player.getName() == null) {
				player.setName("ANÒNIM");
			}
		}

		Link link = linkTo(PlayerController.class).withSelfRel();
		CollectionModel<PlayerDTO> result = CollectionModel.of(allPlayers, link);
		return result;
	}

	@PutMapping("/{player_id}") // UPDATE PLAYER'S NAME
	public ResponseEntity<Message> updatePlayer(@RequestBody PlayerDTO playerDTO, @PathVariable String player_id) {
		try {
			PlayerDTO playerReturned = playerService.getPlayerByID(player_id);
			playerReturned.setName(playerDTO.getName());
			// save change to database
			playerService.replacePlayer(playerReturned);

			if (playerReturned.getName() == null) {
				playerReturned.setName("ANÒNIM");
			}
			return new ResponseEntity<Message>(new Message("Player name successfully updated", playerReturned, ""),
					HttpStatus.OK);

		} catch (DataIntegrityViolationException duplicateError) { // handle duplicated name error
			return new ResponseEntity<Message>(
					new Message("Another Player has this name already!", null, duplicateError.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message("Failed update Player!", null, e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
