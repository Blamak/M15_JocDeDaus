package com.ITAcademy.M15_JocDeDaus.Repositories;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ITAcademy.M15_JocDeDaus.Entities.ERole;
import com.ITAcademy.M15_JocDeDaus.Entities.Role;


public interface IRoleRepository extends MongoRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}