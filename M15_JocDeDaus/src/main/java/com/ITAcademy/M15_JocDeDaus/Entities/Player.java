package com.ITAcademy.M15_JocDeDaus.Entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.ITAcademy.M15_JocDeDaus.DTO.PlayerDTO;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "player")
public class Player {
	
	@Id
	private String id;
	private String name;
	private BigDecimal win_rate;	
	private Date date_registered;
	
	
	// getters & setters:
	
	public BigDecimal getWinRate() {
		return win_rate;
	}

	public void setWinRate(BigDecimal winRate) {
		this.win_rate = winRate;
	}

	public String getPlayer_id() {
		return id;
	}

	public void setPlayer_id(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate_registered() {
		return date_registered;
	}

	public void setDate_registered(Date date_registered) {
		this.date_registered = date_registered;
	}

}
