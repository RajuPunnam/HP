package com.io.common;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import org.springframework.web.filter.RequestContextFilter;

import com.sun.jersey.api.core.ResourceConfig;





@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig {

	private Set<Object> singletons;
	
	public MyApplication()
	{
		System.out.println("MyApplication.No-Arg Construction");
		
		
		singletons = new HashSet<Object>();
		singletons.add(new RequestContextFilter());
		singletons.add(new Resources());
		
		
	}

	@Override
	public boolean getFeature(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Boolean> getFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getProperty(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
