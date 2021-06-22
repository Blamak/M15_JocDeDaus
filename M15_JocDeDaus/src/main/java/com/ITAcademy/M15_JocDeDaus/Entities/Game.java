package com.ITAcademy.M15_JocDeDaus.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "game")
public class Game {
	
	@Id
	private String id;
	private int dice1;
	private int dice2;
	private String result;
	
	private String playerId;
	
	// getters & setters:
	
	public String getId() {
		return id;
	}

	public void setId(String game_id) {
		this.id = game_id;
	}

	public Integer getDice1() {
		return dice1;
	}

	public void setDice1(int dice1) {
		this.dice1 = dice1;
	}

	public Integer getDice2() {
		return dice2;
	}

	public void setDice2(int dice2) {
		this.dice2 = dice2;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	
}
