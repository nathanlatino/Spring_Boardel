package ch.hearc.smb.service;

import ch.hearc.smb.model.PasswordResetToken;
import ch.hearc.smb.repository.PasswordResetTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Calendar;

@Service
public class SecurityService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;


	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;


	private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}
		return null;
	}

	public void autoLogin(String username, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());
		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			logger.trace(MessageFormat.format("Auto login {0} successfully", username));
		}
	}

	public String validatePasswordResetToken(long id, String token) {
		PasswordResetToken passToken =
				passwordResetTokenRepository.findByToken(token);
		if ((passToken == null) || (passToken.getUser()
				.getId() != id)) {
			return "invalidtoken";
		}

		Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate()
				.getTime() - cal.getTime()
				.getTime()) <= 0) {
			return "expired";
		}

		return null;
	}
}
