package com.ITAcademy.M15_JocDeDaus.DTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "players") // change array name in _embedded HAL return type
//public class PlayerDTO extends RepresentationModel<PlayerDTO> {
public class PlayerDTO {
	
	private String id;
	private String name;
	private Double winRate;
	

	//  create a “current time” JDBC Timestamp
	private Date date_registered = new Date();
	
	// getters & setters:
	
	public Double getWinRate() {
		return winRate;
	}
	
	public void setWinRate(Double winRate) {
		this.winRate = winRate;
	}

	public String getId() {
		return this.id;
	}
	
	public void setId(String player_id) {
		this.id = player_id;
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
