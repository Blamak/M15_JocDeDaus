package com.ITAcademy.M15_JocDeDaus;

import java.sql.Timestamp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ITAcademy.M15_JocDeDaus.Entities.Player;
import com.ITAcademy.M15_JocDeDaus.Repositories.IPlayerRepository;

@SpringBootApplication
public class M15JocDeDausApplication {

	public static void main(String[] args) {
		SpringApplication.run(M15JocDeDausApplication.class, args);
	}
	
//	@Bean
//	public CommandLineRunner demo(IPlayerRepository playerRepository) {
//		return (args) -> {
//			// save players
//			playerRepository.save(new Player(0, "SpongeBob", null, new Timestamp(System.currentTimeMillis())));
//			playerRepository.save(new Player(0, "Patrick", null, new Timestamp(System.currentTimeMillis())));
//			playerRepository.save(new Player(0, "SquidWard", null, new Timestamp(System.currentTimeMillis())));
//		};
//	}

}
