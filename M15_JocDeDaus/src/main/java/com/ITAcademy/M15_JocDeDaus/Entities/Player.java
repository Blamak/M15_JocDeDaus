package com.ITAcademy.M15_JocDeDaus.Entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Player")
public class Player {
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long player_id;
	@Column
	private String name;
	@Column(name = "date")
	private Timestamp currentTime;
	
	@OneToMany(mappedBy = "player")
	private List<Game> games;


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

	public Timestamp getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Timestamp currentTime) {
		this.currentTime = currentTime;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

}
