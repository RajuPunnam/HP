package com.techouts.resources;

import java.text.ParseException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techouts.service.SkuAvalibility;

@Component
@Path("/PADataProcess")
public class SkuAvailCal {
	static final Logger LOGGER = Logger.getLogger(SkuAvailCal.class);
	@Autowired
	private SkuAvalibility skuAvalibility;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/SKUCal")
	public Response getCalCulateSkuAvailability() throws ParseException {
		String sucessMessage = "";

		return Response.ok(sucessMessage).build();
	}
}
