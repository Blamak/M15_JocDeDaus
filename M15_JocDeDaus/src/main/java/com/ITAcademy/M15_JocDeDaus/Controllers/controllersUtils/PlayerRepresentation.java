package com.ITAcademy.M15_JocDeDaus.Controllers.controllersUtils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.RepresentationModel;

import com.ITAcademy.M15_JocDeDaus.Controllers.PlayerController;
import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;

public class PlayerRepresentation extends RepresentationModel<PlayerRepresentation>{
	
	private final PlayerDTO player;
	
	public PlayerRepresentation(final PlayerDTO player) {
		this.player = player;
		final String player_id = player.getId();
		add(linkTo(methodOn(PlayerController.class).retrievePlayer(player_id)).withSelfRel());
	}

	public PlayerDTO getPlayer() {
		return player;
	}

}
