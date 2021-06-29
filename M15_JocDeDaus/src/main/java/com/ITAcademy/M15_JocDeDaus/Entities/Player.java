package com.ITAcademy.M15_JocDeDaus.Entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column
	private BigDecimal win_rate;	
	@Column
	private Timestamp date_registered;
	
	public Player() {
		
	}
	
	public Player(long player_id, String name, BigDecimal win_rate, Timestamp date_registered) {
		super();
		this.player_id = player_id;
		this.name = name;
		this.win_rate = win_rate;
		this.date_registered = date_registered;
	}

	public BigDecimal getWinRate() {
		return win_rate;
	}

	public void setWinRate(BigDecimal winRate) {
		this.win_rate = winRate;
	}

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

	public Timestamp getDate_registered() {
		return date_registered;
	}

	public void setDate_registered(Timestamp date_registered) {
		this.date_registered = date_registered;
	}

}
