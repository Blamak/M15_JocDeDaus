package com.ITAcademy.M15_JocDeDaus.Controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.RepresentationModel;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;

public class PlayerRepresentation extends RepresentationModel<PlayerRepresentation>{
	
	private final PlayerDTO player;
	
	public PlayerRepresentation(final PlayerDTO player) {
		this.player = player;
		final long player_id = player.getPlayer_id();
		add(linkTo(PlayerController.class).withRel("all-players"));
		add(linkTo(methodOn(PlayerController.class).updatePlayer(player, player_id)).withRel("update player"));
	}

	public PlayerDTO getPlayer() {
		return player;
	}

}
