package com.scorpio4.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.test
 * @author lee
 * Date  : 14/07/2014
 * Time  : 7:00 PM
 */

@Path("/test/jaxrs/{assetURI}")
public class JAXRS  {
	private static final Logger log = LoggerFactory.getLogger(JAXRS.class);

	@GET
	public Response get(@PathParam("assetURI") final String assetURI, @Context UriInfo uriInfo) {
		log.warn("JAXRS: "+assetURI+" -> "+uriInfo.getRequestUri());
		return Response.ok("hello: "+assetURI).build();
	}

}
