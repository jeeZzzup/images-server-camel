package fr.etai.iserver.rest;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
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

@Path("/")
public class ImagesAccessorService {

	Logger logger = LoggerFactory.getLogger(ImagesAccessorService.class);

	@GET
	@Path("/images/{image}")
	@Produces("image/*")
	public Response getImage(@HeaderParam("hmac")
	String hmac, @PathParam("image")
	String image) {

		if (StringUtils.isEmpty(hmac) || !accept(hmac)) {
			throw new WebApplicationException(405); // TODO
		}

		File f = new File("C://images//" + image);
		if (!f.exists()) {
			throw new WebApplicationException(404); // TODO
		}

		String mt = new MimetypesFileTypeMap().getContentType(f);
		return Response.ok(f, mt).build();
	}

	private boolean accept(String hmac) {

		String[] splittedHmac = hmac.split(":");
		String messageToEncryptWithHmac = splittedHmac[0];
		String messageEncryptedWithHmac = splittedHmac[1];

		try {
			String hmacMd5 = hmacMd5(messageToEncryptWithHmac, "secretKey");
			logger.info(" ? [{}] == [{}]", messageEncryptedWithHmac, hmacMd5);
			return messageEncryptedWithHmac.equals(hmacMd5);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	private String hmacMd5(String data, String key) throws Exception {

		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacMD5");
		Mac mac = Mac.getInstance("HmacMD5");
		mac.init(secretKey);

		byte[] hmacData = mac.doFinal(data.getBytes());

		return toHEX(hmacData);
	}

	private static String toHEX(byte[] digest) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < digest.length; ++i) {
			String hx = Integer.toHexString(0xFF & digest[i]);
			if (hx.length() == 1) {
				hx = "0" + hx;
			}
			hexString.append(hx);
		}
		return hexString.toString();
	}
}
