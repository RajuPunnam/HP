package com.techouts.hp.pojo;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@Document(collection="RawDataUsers")
public class User implements UserDetails 
{
	@Field("username")
	private String username;
	@Field("password")
	private String password;
	@Field("isActive")
	private boolean isActive;
	@Field("roles")
	private String roles;
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public boolean isActive()
	{
		return isActive;
	}
	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}
	public String getRoles()
	{
		return roles;
	}
	public void setRoles(String roles)
	{
		this.roles = roles;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAccountNonExpired()
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked()
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired()
	{
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
