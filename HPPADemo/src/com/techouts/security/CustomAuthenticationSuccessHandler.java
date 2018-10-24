package com.techouts.security;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import com.techouts.pojo.User;
import com.techouts.services.HpServices;
import com.techouts.util.ShaAlgorithm;

@Controller
public class CustomAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {
	private static final Logger LOG=Logger.getLogger(CustomAuthenticationSuccessHandler.class);
	@Autowired
	private HpServices hpServices;
	

	/*public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

		System.out
				.println("CustomerAuthenticationSuccessHandler.defaultAfterLogin");
		Set<String> roles = AuthorityUtils.authorityListToSet(auth
				.getAuthorities());

		String path = request.getContextPath();
		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect(path + "/AdminControl.xhtml");
			return;
		} else if (roles.contains("ROLE_SCM")) {
			response.sendRedirect(path + "/Welcome.xhtml");
			return;
		} else if (roles.contains("ROLE_SALES")) {
			response.sendRedirect(path + "/ProductAvailbility.xhtml");
			return;
		}
		response.sendRedirect(path + "/ProductAvailbility.xhtml");
	}
	*/

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {

	
		String username = auth.getName();
		
		
		User user = hpServices.getCurrentUser(username);
		System.out
				.println("CustomerAuthenticationSuccessHandler.defaultAfterLogin");
		Set<String> roles = AuthorityUtils.authorityListToSet(auth
				.getAuthorities());

		
		String path = null;
		if(request.getHeader("User-Agent").indexOf("Mobile") != -1){
			
			if(user.getLastLogin()==0){
				user.setLastLogin(new Date().getTime());
				user.setCurrentLogin(new Date().getTime());
				hpServices.updateUserLogin(user);
				if(user.getRoles().endsWith("ROLE_ADMIN")){
				path = request.getContextPath() ;
				LOG.info("MOBILE USER");
				response.sendRedirect(path + "/AdminControlMob.xhtml");
				}else{
					
					path = request.getContextPath() ;
					LOG.info("MOBILE USER");
					response.sendRedirect(path + "/UserInformation.xhtml");
						
				}
				
			}else{
					user.setLastLogin(user.getCurrentLogin());
					user.setCurrentLogin(new Date().getTime());
					hpServices.updateUserLogin(user);
			
				
				path = request.getContextPath() ;
				LOG.info("MOBILE USER");
			response.sendRedirect(path + "/search.xhtml");
			}
			}
		else{
			path = request.getContextPath() ;
			if(user.getLastLogin()==0){
				
				user.setLastLogin(new Date().getTime());
				user.setCurrentLogin(new Date().getTime());
				hpServices.updateUserLogin(user);
		if (roles.contains("ROLE_ADMIN")) {
			 
			response.sendRedirect(path + "/AdminControl.xhtml?faces-redirect=true");
			return;
		} else if (roles.contains("ROLE_SCM")) {
			
			response.sendRedirect(path + "/UserInfo.xhtml?faces-redirect=true");
			return;
		} else if (roles.contains("ROLE_SALES")) {
			 
			response.sendRedirect(path + "/UserInfo.xhtml?faces-redirect=true");
			return ;
		}
		//response.sendRedirect(path + "/newhome.xhtml?faces-redirect=true");
	}else{
		
			
			user.setLastLogin(new Date().getTime());
			user.setCurrentLogin(new Date().getTime());
			hpServices.updateUserLogin(user);
	
		if (roles.contains("ROLE_ADMIN")) {
			 
			response.sendRedirect(path + "/newhome.xhtml?faces-redirect=true");
			return;
		} else if (roles.contains("ROLE_SCM")) {
			
			response.sendRedirect(path + "/dishome.xhtml?faces-redirect=true");
			return;
		} else if (roles.contains("ROLE_SALES")) {
			 
			response.sendRedirect(path + "/newhome.xhtml?faces-redirect=true");
			return ;
		}
		response.sendRedirect(path + "/newhome.xhtml?faces-redirect=true");
		
		
	}
		}
	}
}
