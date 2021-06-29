package com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.RepresentationModel;

import com.ITAcademy.M15_JocDeDaus.Controllers.GameController;
import com.ITAcademy.M15_JocDeDaus.Controllers.PlayerController;
import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;

/*
 * Class to represent the Player entity in the HATEOAS implementation,
 * so that not only data but also links to related resources are returned.
 */

public class PlayerRepresentation extends RepresentationModel<PlayerRepresentation>{
	
	private final PlayerDTO player;
	
	public PlayerRepresentation(final PlayerDTO player) {
		this.player = player;
		final long player_id = player.getPlayer_id();
		add(linkTo(methodOn(PlayerController.class).retrievePlayer(player_id)).withSelfRel());
		add(linkTo(methodOn(GameController.class).retrievePlayerGames(player_id)).withRel("all-games"));
	}

	public PlayerDTO getPlayer() {
		return player;
	}
	
	public Long getPlayer_id() {
		return this.player.getPlayer_id();
	}

	public String getName() {
		return this.player.getName();
	}

}
