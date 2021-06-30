package com.ITAcademy.M15_JocDeDaus.DTO;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.springframework.hateoas.server.core.Relation;

public class PlayerDTO {

	private long id;
	private String name;
	private BigDecimal win_rate = new BigDecimal("0.0");
	private Timestamp date_registered = new Timestamp(System.currentTimeMillis());

	public BigDecimal getWin_rate() {
		return win_rate;
	}

	public void setWin_rate(BigDecimal win_rate) {
		this.win_rate = win_rate;
	}

	public Long getId() {
		return id;
	}

	public void setPlayer_id(long id) {
		this.id = id;
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
