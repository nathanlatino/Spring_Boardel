package ch.hearc.boardel.service;

import ch.hearc.boardel.model.CustomUser;
import ch.hearc.boardel.model.Role;
import ch.hearc.boardel.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private CustomUserRepository customUserRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		CustomUser customUser = customUserRepository.findByUsername(username);
		if (customUser == null)
			throw new UsernameNotFoundException(username);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : customUser.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

		}
		return new org.springframework.security.core.userdetails.User(customUser.getUsername(),
				customUser.getPassword(), grantedAuthorities);
	}

}
