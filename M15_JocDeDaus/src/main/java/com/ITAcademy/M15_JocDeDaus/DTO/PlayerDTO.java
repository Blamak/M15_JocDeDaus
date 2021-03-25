package com.ITAcademy.M15_JocDeDaus.DTO;

import java.sql.Timestamp;
import java.util.List;

public class PlayerDTO {
	
	private Long player_id;
	private String name;
	
	//  create a “current time” JDBC Timestamp
	private Timestamp currentTime = new Timestamp(System.currentTimeMillis());
	
	private List<GameDTO> games;
	
	
	// getters & setters:
	
	public Long getPlayer_id() {
		return player_id;
	}
	
	public void setPlayer_id(Long player_id) {
		this.player_id = player_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Timestamp getCurrentTime() {
		return currentTime;
	}
	
	public void setCurrentTime(Timestamp currentTime) {
		this.currentTime = currentTime;
	}
	
	public List<GameDTO> getGames() {
		return games;
	}
	
	public void setGames(List<GameDTO> games) {
		this.games = games;
	}

}
