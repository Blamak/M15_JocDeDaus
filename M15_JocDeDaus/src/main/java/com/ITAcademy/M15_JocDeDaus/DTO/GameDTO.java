package com.ITAcademy.M15_JocDeDaus.DTO;

import java.util.HashSet;
import java.util.Set;

import com.ITAcademy.M15_JocDeDaus.Entities.Game;

public class GameDTO {

	private long game_id;
	private int dice1;
	private int dice2;
	private String result;
	private PlayerDTO player;

	
	// getters & setters:
	

	public Long getGame_id() {
		return game_id;
	}

	public void setGame_id(long game_id) {
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

	public PlayerDTO getPlayer() {
		return player;
	}

	public void setPlayer(PlayerDTO player) {
		this.player = player;
	}

}
