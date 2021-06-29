package com.ITAcademy.M15_JocDeDaus.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class PlayerDTO {

	private String id;
	private String name;
	private BigDecimal winRate = new BigDecimal("0.0");
	private Date date_registered = new Date();
	
	public BigDecimal getWinRate() {
		return winRate;
	}
	
	public void setWinRate(BigDecimal winRate) {
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
