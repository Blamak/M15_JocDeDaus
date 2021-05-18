package com.ITAcademy.M15_JocDeDaus.Controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.xml.crypto.URIReferenceException;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.hibernate.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;
import com.ITAcademy.M15_JocDeDaus.Exceptions.ExceptionResponse;
import com.ITAcademy.M15_JocDeDaus.Exceptions.PlayerNotFoundException;
import com.ITAcademy.M15_JocDeDaus.Response.Message;
import com.ITAcademy.M15_JocDeDaus.Services.IGameService;
import com.ITAcademy.M15_JocDeDaus.Services.IPlayerService;

@RestController
@RequestMapping("/players")
//@EnableHypermediaSupport(type = HypermediaType.HAL)
public class PlayerController {

	@Autowired
	private IPlayerService playerService;

	@Autowired
	private IGameService gameService;

	@PostMapping("") // CREATE NEW PLAYER
	public ResponseEntity<Message> addPlayer(@RequestBody PlayerDTO playerDTO) {
		try {
			PlayerDTO playerReturned = playerService.savePlayer(playerDTO);

			return new ResponseEntity<Message>(new Message("Player created successfully!", playerReturned, ""),
					HttpStatus.CREATED);
		} catch (DataIntegrityViolationException duplicateError) { // handle duplicated name error
			return new ResponseEntity<Message>(
					new Message("Another player already has this name!", null, duplicateError.getMessage()),
					HttpStatus.CONFLICT);
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
	
//    return personRepository.findById(id).map(p - > ResponseEntity.ok(new PersonResource(p))).orElseThrow(() - > new PersonNotFoundException(id));

	
	
//	public EntityModel<PlayerDTO> retrievePlayer(@PathVariable long player_id) {
//		PlayerDTO player = playerService.getPlayerByID(player_id);
//		EntityModel<PlayerDTO> resource = EntityModel.of(player);
//
//		WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllPlayers());
//		resource.add(linkTo.withRel("all-users"));
//
//		return resource;
//	}

	@GetMapping("")
	public CollectionModel<PlayerDTO> retrieveAllPlayers() {
		List<PlayerDTO> allPlayers = playerService.getAllPlayers();

//		for (PlayerDTO player : allPlayers) {
//			long playerID = player.getPlayer_id();
//			Link selfLink = linkTo(PlayerController.class).slash(playerID).withSelfRel();
//			player.add(selfLink);
//			if (gameService.gamesByPlayer(playerID).size() > 0) {
//				Link gamesLink = linkTo(methodOn(GameController.class).retrievePlayerGames(playerID))
//						.withRel("player's games");
//				player.add(gamesLink);
//			}
//		}

		Link link = linkTo(PlayerController.class).withSelfRel();
		CollectionModel<PlayerDTO> result = CollectionModel.of(allPlayers, link);
		return result;
	}
	
	

//	@GetMapping("") // READ ALL PLAYERS
//	public ResponseEntity<Resources> retrieveAllPlayers() {
//		try {
//			List<PlayerDTO> playersList = playerService.getAllPlayers();
//
//			return new ResponseEntity<Message>(new Message("Players' list successfully retrieved", playersList, ""),
//					HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<Message>(
//					new Message("Failed to get Players' list!", (List<PlayerDTO>) null, e.getMessage()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//
//		}
//	}

	@PutMapping("/{player_id}") // UPDATE PLAYER'S NAME
	public ResponseEntity<Object> updatePlayer(@RequestBody PlayerDTO playerDTO, @PathVariable long player_id) {
//		try {
		PlayerDTO playerReturned = playerService.getPlayerByID(player_id);

		if (playerReturned == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ertewydasdr");
		// set new name
		playerReturned.setName(playerDTO.getName());

		// save change to database
		playerService.replacePlayerName(playerReturned);

//			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("").toUri();

//			return ResponseEntity.created(location).build();
		return ResponseEntity.created(null).build();

//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body("Unable to update");
//		}

//		EntityModel<PlayerDTO> playerResource = EntityModel.of(playerReturned);
//		
//		try {
//			return ResponseEntity.created(new URI(playerResource.getRequiredLink(Link.REL_SELF).getHref()))
//				.body(employeeResource);
//		} catch (URISyntaxException e) {
//			return ResponseEntity.badRequest().body("Unable to update " + employee);
//		}
//		
//		try {
//			if (playerService.checkPlayerExists(player_id)) {
//				PlayerDTO playerReturned = playerService.getPlayerByID(player_id);
//				// set new name
//				playerReturned.setName(playerDTO.getName());
//
//				// save change to database
//				playerService.replacePlayerName(playerReturned);
//
//				return new ResponseEntity<Message>(new Message("Player name successfully updated", playerReturned, ""),
//						HttpStatus.OK);
//			} else {
//				// id not corresponding to any player case
//				return new ResponseEntity<Message>(
//						new Message("Player with id = " + player_id + " not found!!", null, ""), HttpStatus.NOT_FOUND);
//			}
//		} catch (DataIntegrityViolationException duplicateError) { // handle duplicated name error
//			return new ResponseEntity<Message>(
//					new Message("Another Player has this name already!", null, duplicateError.getMessage()),
//					HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			return new ResponseEntity<Message>(new Message("Failed update Player!", null, e.getMessage()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}

}
