package com.ITAcademy.M15_JocDeDaus.Entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "roles")
public class Role {
  @Id
  private String id;

  @Field
  private ERole name;

  
  
  public Role(String id, ERole name) {
	super();
	this.id = id;
	this.name = name;
}

public Role() {

  }

  public Role(ERole name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ERole getName() {
    return name;
  }

  public void setName(ERole name) {
    this.name = name;
  }
}
