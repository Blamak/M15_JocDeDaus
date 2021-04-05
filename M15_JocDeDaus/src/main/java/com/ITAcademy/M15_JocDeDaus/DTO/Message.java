package com.ITAcademy.M15_JocDeDaus.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ITAcademy.M15_JocDeDaus.Entities.Game;
import com.ITAcademy.M15_JocDeDaus.Entities.Player;

public class Message {
	
	private String message = "";
//	private List<PlayerDTO> players = new ArrayList<PlayerDTO>();
	Object object;
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	private String error = "";
//	Game game = new Game();

//	public Message(String message, List<PlayerDTO> players, String error) {
//		this.message = message;
//		this.players = players;
//		this.error = error;
//	}
	
	public Message(String message, Object object, String error) {
		this.message = message;
		this.object = object;
		this.error = error;
	}
	
//	public Message(String message, Game game, String error) {
//		this.message = message;
//		this.game = game;
//		this.error = error;
//	}
	
	
	public Message() {
	
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

//	public List<PlayerDTO> getPlayers() {
//		return players;
//	}
//
//	public void setPlayers(List<PlayerDTO> players) {
//		this.players = players;
//	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

//	public Game getGame() {
//		return game;
//	}
//
//	public void setGame(Game game) {
//		this.game = game;
//	}

}
