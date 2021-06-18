package com.ITAcademy.M15_JocDeDaus.Entities;

import org.springframework.data.annotation.Id;

public class Game {
	
	@Id
	private long game_id;
	private int dice1;
	private int dice2;
	private String result;
	
//	private Player player;
	
	// getters & setters:
	
	public Long getGame_id() {
		return game_id;
	}

	public void setGames_id(long game_id) {
		this.game_id = game_id;
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

//	public Player getPlayer() {
//		return player;
//	}
//
//	public void setPlayer(Player player) {
//		this.player = player;
//	}
	
}
