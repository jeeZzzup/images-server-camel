package fr.etai.iserver.eip;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IServerRouteBuilder extends RouteBuilder {

	@Autowired
	IServerResizeProcessor iServerResizeProcessor;

	@Value("${input.folder.uri}")
	private String inputFolderURI;

	@Value("${work.folder.uri}")
	private String workFolderURI;

	@Value("${output.folder.uri}")
	private String outputFolderURI;

	@Value("${route.log}")
	private String routeLog;

	public String getInputFolderURI() {
		return inputFolderURI;
	}

	public String getWorkFolderURI() {
		return workFolderURI;
	}

	public String getOutputFolderURI() {
		return outputFolderURI;
	}

	public String getRouteLog() {
		return routeLog;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see camel-context.properties
	 * */
	public void configure() throws Exception {

		from(inputFolderURI).to(routeLog).process(iServerResizeProcessor)
				.to(workFolderURI);

		from(workFolderURI).to(routeLog).to(outputFolderURI);
	}

}
