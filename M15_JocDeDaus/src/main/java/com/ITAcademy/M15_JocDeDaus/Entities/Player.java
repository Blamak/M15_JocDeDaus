package com.ITAcademy.M15_JocDeDaus.Entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.springframework.hateoas.RepresentationModel;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;

import org.springframework.data.annotation.Id;

public class Player {
	
	@Id
	private long player_id;
	private String name;
//	private BigDecimal win_rate;	
//	private Timestamp date_registered;
	
	
	// getters & setters:
	
//	public BigDecimal getWinRate() {
//		return win_rate;
//	}
//
//	public void setWinRate(BigDecimal winRate) {
//		this.win_rate = winRate;
//	}

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

//	public Timestamp getDate_registered() {
//		return date_registered;
//	}
//
//	public void setDate_registered(Timestamp date_registered) {
//		this.date_registered = date_registered;
//	}

}
