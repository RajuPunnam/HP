package com.techouts.security;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.techouts.pojo.ConfirmedOrder;
import com.techouts.pojo.User;
import com.techouts.services.HpServices;
import com.techouts.util.ShaAlgorithm;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private HpServices hpServices;

	ExecutorService executor;
	List<ConfirmedOrder> confirmedOrders;

	private final static Logger LOGGER = Logger.getLogger(CustomAuthenticationProvider.class);

	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		String username = auth.getName();
		String password = null;
		password = ShaAlgorithm.encriptData(auth.getCredentials().toString());
		LOGGER.info("GET Values are :" + username + "\t Password :" + password
				+ "\t Authorities :" + auth.getAuthorities());

		User user = hpServices.getAuthenticate(username, password);

		if (user != null && user.isActive()) {
			List<GrantedAuthority> grantedAuth = new ArrayList<GrantedAuthority>();
			Set<String> roles = hpServices.getUserRoles(user.getRoles());
			for (String r : roles) {
				grantedAuth.add(new GrantedAuthorityImpl(r));
			}
			LOGGER.info("Authenticated Successfully");
			hpServices.updateUserLogin(user);

			return new UsernamePasswordAuthenticationToken(username, password,
					grantedAuth);

		} else {
			LOGGER.info("Invalid Credentials");
			throw new BadCredentialsException("Invalid user Name or Password");

		}

	}

	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
