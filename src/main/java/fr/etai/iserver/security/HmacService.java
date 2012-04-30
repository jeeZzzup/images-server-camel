package fr.etai.iserver.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HmacService {

	final Logger logger = LoggerFactory.getLogger(HmacService.class);

	public boolean accept(String hmac) {

		String[] splittedHmac = hmac.split(":");
		String messageToEncryptWithHmac = splittedHmac[0];
		String messageEncryptedWithHmac = splittedHmac[1];

		try {
			String hmacMd5 = hmacMd5(messageToEncryptWithHmac, "secretKey");
			logger.debug(" ? [{}] == [{}]", messageEncryptedWithHmac, hmacMd5);
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
