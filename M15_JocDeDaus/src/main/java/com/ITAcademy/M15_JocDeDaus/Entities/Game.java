package com.ITAcademy.M15_JocDeDaus.Entities;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;

@Entity
@Table(name = "Game")
public class Game {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long games_id;
	@Column
	private int dice1;
	@Column
	private int dice2;
	@Column
	private String result;
	
	@JoinColumn(name = "player_id")
	@ManyToOne
	private Player player;
	

	// getters & setters:
	
	public Long getGames_id() {
		return games_id;
	}

	public void setGames_id(long game_id) {
		this.games_id = game_id;
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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
