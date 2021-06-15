package com.ITAcademy.M15_JocDeDaus.DTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "players") // change array name in _embedded HAL return type
//public class PlayerDTO extends RepresentationModel<PlayerDTO> {
public class PlayerDTO {
	
	private long player_id;
	private String name;
	private BigDecimal win_rate;
	

	//  create a “current time” JDBC Timestamp
	private Timestamp date_registered = new Timestamp(System.currentTimeMillis());
	
	// getters & setters:
	
	public BigDecimal getWin_rate() {
		return win_rate;
	}
	
	public void setWin_rate(BigDecimal win_rate) {
		this.win_rate = win_rate;
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
