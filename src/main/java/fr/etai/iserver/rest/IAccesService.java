package fr.etai.iserver.rest;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.etai.iserver.dao.ImagesDao;
import fr.etai.iserver.security.HmacService;

@Component
@Path("/access")
public class IAccesService {

	final Logger logger = LoggerFactory.getLogger(IAccesService.class);

	@Autowired
	private ImagesDao imagesDao;

	@GET
	@Path("/name/{image}")
	@Produces("image/*")
	public Response getImage(@HeaderParam("hmac")
	String hmac, @PathParam("image")
	String imageName) {

		if (StringUtils.isEmpty(hmac) || !new HmacService().accept(hmac)) {
			throw new WebApplicationException(405); // forbidden
		}

		File file = imagesDao.getFileByName(imageName);
		if (null == file) {
			throw new WebApplicationException(404); // not found
		}

		String mt = new MimetypesFileTypeMap().getContentType(file);
		return Response.ok(file, mt).build();
	}
}
