package com.techouts.hp.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;

import com.techouts.hp.pojo.User;
import com.techouts.hp.service.impl.UserService;

@SuppressWarnings("deprecation")
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final static Logger LOGGER = Logger
			.getLogger(CustomAuthenticationProvider.class);
	@Autowired
	private UserService userService;

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		User user = userService.loadUserByUsername(username, password);
		LOGGER.info(user);
		if (user != null && user.isActive()) {
			List<GrantedAuthority> grantedAuth = new ArrayList<GrantedAuthority>();
			Set<String> roles = userService.getUserRoles(user.getRoles());
			for (String r : roles) {
				grantedAuth.add(new GrantedAuthorityImpl(r));
			}
			return new UsernamePasswordAuthenticationToken(username, password,
					grantedAuth);

		} else {
			throw new BadCredentialsException(
					"Invalid Credentials / custom message");
		}
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

}