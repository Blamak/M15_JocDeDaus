package com.ITAcademy.M15_JocDeDaus.DTO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.ITAcademy.M15_JocDeDaus.Entities.Game;

public class PlayerDTO {
	
	private long player_id;
	private String name;
	
	//  create a “current time” JDBC Timestamp
	private Timestamp date = new Timestamp(System.currentTimeMillis());
//	private List<Game> games;
	
	
	// getters & setters:
	
	public Long getPlayer_id() {
		return player_id;
	}
	
	public void setPlayer_id(long player_id) {
		this.player_id = player_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
	}

//	public List<Game> getGames() {
//		return games;
//	}
//
//	public void setGames(List<Game> games) {
//		this.games = games;
//	}
//	
//	public void addGame(Game game ) {
//		this.games.add(game);
//	}
//	
}
