package fr.etai.iserver.eip;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImagesServerRouteBuilder extends RouteBuilder {

	private static final String LOG_MODELE_PROD = "log:fr.etai?showAll=true&multiline=true";

	/**
	 * Pooling directory. ReadOnly. TODO filter using regex ? filename ?
	 */
	private static final String FOLDER__MODELE_PROD = "file:data/srv-img-prod/modele?noop=true";

	private static final String FOLDER__MODELE_TMP = "file:data/srv-img-tmp/modele";

	private static final String FOLDER__MODELE_EXPLOIT = "file:data/srv-img-exploit/modele";

	private static final String FOLDER_OUT_DEFAULT = "file:data/outDefault";

	@Autowired
	ImagesServerResizeProcessor imagesServerResizeProcessor;

	/**
	 * {@inheritDoc}
	 * */
	public void configure() throws Exception {

		from(FOLDER__MODELE_PROD).to(LOG_MODELE_PROD)
				.process(imagesServerResizeProcessor).to(FOLDER__MODELE_TMP);

		from(FOLDER__MODELE_TMP).to(LOG_MODELE_PROD).to(FOLDER__MODELE_EXPLOIT);
	}

}
