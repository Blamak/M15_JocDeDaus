package com.ITAcademy.M15_JocDeDaus;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ITAcademy.M15_JocDeDaus.Entities.ERole;
import com.ITAcademy.M15_JocDeDaus.Entities.Role;
import com.ITAcademy.M15_JocDeDaus.Repositories.IRoleRepository;

@SpringBootApplication
public class M15JocDeDausApplication {

	public static void main(String[] args) {
		SpringApplication.run(M15JocDeDausApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IRoleRepository roleRepository) {
		return (args) -> {
			// save roles
			if (roleRepository.findByName(ERole.ROLE_ADMIN) != null) {
				roleRepository.save(new Role(null, ERole.ROLE_ADMIN));
			}
			if (roleRepository.findByName(ERole.ROLE_MODERATOR) != null) {
				roleRepository.save(new Role(null, ERole.ROLE_MODERATOR));
			}
			if (roleRepository.findByName(ERole.ROLE_USER) != null) {
				roleRepository.save(new Role(null, ERole.ROLE_USER));
			}
		};
	}

}
