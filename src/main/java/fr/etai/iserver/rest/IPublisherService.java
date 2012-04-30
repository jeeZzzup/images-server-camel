package fr.etai.iserver.rest;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/publish")
public class IPublisherService {

	@PUT
	@Path("/{type}/{data}")
	public void publish(String type, String data) {
		// TODO impl
	}

}
