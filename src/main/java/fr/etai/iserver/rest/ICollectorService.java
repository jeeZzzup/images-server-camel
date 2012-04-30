package fr.etai.iserver.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.view.Viewable;

import fr.etai.iserver.eip.IServerRouteBuilder;

@Component
@Path("/collect")
public class ICollectorService {

	final Logger logger = LoggerFactory.getLogger(ICollectorService.class);

	@Autowired
	IServerRouteBuilder iServerRouteBuilder;

	@GET
	@Path("/about")
	@Produces(MediaType.TEXT_HTML)
	public Response about() {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> poolingInformations = new HashMap<String, Object>();
		poolingInformations.put("Input Folder",
				iServerRouteBuilder.getInputFolderURI());
		poolingInformations.put("Working Folder",
				iServerRouteBuilder.getWorkFolderURI());
		poolingInformations.put("Output Folder",
				iServerRouteBuilder.getOutputFolderURI());
		poolingInformations.put("Output Log",
				iServerRouteBuilder.getRouteLog());
		params.put("poolingInformations", poolingInformations);
		return Response.ok(new Viewable("/jsp/about", params)).build();
	}
}
