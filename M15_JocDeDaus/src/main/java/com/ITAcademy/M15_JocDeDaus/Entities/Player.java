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
	private long id;
	@Column
	private String name;
	@Column
	private double win_rate;	
	@Column
	private Timestamp date_registered;
	
	public Player() {
		
	}
	
	public Player(long id, String name, double win_rate, Timestamp date_registered) {
		super();
		this.id = id;
		this.name = name;
		this.win_rate = win_rate;
		this.date_registered = date_registered;
	}

	public double getWinRate() {
		return win_rate;
	}

	public void setWinRate(double winRate) {
		this.win_rate = winRate;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
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
