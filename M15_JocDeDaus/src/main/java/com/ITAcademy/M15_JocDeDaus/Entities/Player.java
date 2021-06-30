package com.ITAcademy.M15_JocDeDaus.Entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
	String password;
	@Field(targetType = FieldType.DECIMAL128)
	private BigDecimal winRate;
	@Field
	private Date date_registered;
	@DBRef
	private Set<Role> roles = new HashSet<>();

	public Player(String id, String name, String password, Date date_registered) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.date_registered = date_registered;
	}

	public Player() {
	}

	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getWinRate() {
		return winRate;
	}

	public void setWinRate(BigDecimal winRate) {
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
