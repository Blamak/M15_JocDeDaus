package com.ITAcademy.M15_JocDeDaus.DTO;

import java.util.ArrayList;
import java.util.List;

public class Message {
	
	private String message = "";
	private List<PlayerDTO> players = new ArrayList<PlayerDTO>();
	private String error = "";

	public Message(String message, List<PlayerDTO> players, String error) {
		this.message = message;
		this.players = players;
		this.error = error;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<PlayerDTO> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerDTO> players) {
		this.players = players;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
	

}
