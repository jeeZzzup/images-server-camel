package fr.etai.iserver.dao;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.WebApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImagesDao {

	final Logger logger = LoggerFactory.getLogger(ImagesDao.class);

	@Autowired
	@Value("${output.folder.uri}")
	private String imagesStoragePath;

	/**
	 * Return the {@link File} defined by this <code>imageName</code> or null.
	 */
	public File getFileByName(String imageName) {

		URL imagesStoragePathURL;
		try {
			imagesStoragePathURL = new URL(imagesStoragePath);
		}
		catch (MalformedURLException e) {
			logger.error("error during retrieve the images-storage path", e);
			throw new WebApplicationException(500); // Internal Server Error
		}

		File file = new File(imagesStoragePathURL.getFile(), imageName);

		return file.exists() ? file : null;
	}

}
