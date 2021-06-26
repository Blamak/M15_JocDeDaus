package com.ITAcademy.M15_JocDeDaus.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ITAcademy.M15_JocDeDaus.Entities.Player;
import com.ITAcademy.M15_JocDeDaus.Repositories.IPlayerRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	IPlayerRepository playerRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Player player = playerRepository.findByName(id);

		return UserDetailsImpl.build(player);
	}

}
