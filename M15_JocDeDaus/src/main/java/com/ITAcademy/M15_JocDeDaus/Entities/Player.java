package com.ITAcademy.M15_JocDeDaus.Entities;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "player")
public class Player {
	
	@Id
	private String id;
	@Field
	private String name;
	@Field
	private double winRate;
	@Field
	private Date date_registered;
	
	
	// getters & setters:
	
	public double getWinRate() {
		return winRate;
	}

	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
