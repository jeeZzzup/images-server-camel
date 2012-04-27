package fr.etai.iserver.eip;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ImagesServerResizeProcessor implements Processor {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public void process(Exchange exchange) throws Exception {
		logger.info("Process {}", exchange.getIn());
	}

}
