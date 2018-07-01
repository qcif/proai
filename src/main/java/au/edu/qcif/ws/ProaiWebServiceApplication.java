package au.edu.qcif.ws;

import java.io.IOException;

import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.ext.swagger.Swagger2SpecificationRestlet;
import org.restlet.ext.swagger.SwaggerApplication;
import org.restlet.routing.Router;
import org.restlet.security.ChallengeAuthenticator;

import au.edu.qcif.ws.resources.RecordResource;
import au.edu.qcif.ws.security.TokenBasedVerifier;



public class ProaiWebServiceApplication  extends SwaggerApplication {
	@Override
	public synchronized Restlet createInboundRoot() {
		ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(), ChallengeScheme.HTTP_OAUTH_BEARER,
				"redboxRealm");
		TokenBasedVerifier verifier = new TokenBasedVerifier();
		guard.setVerifier(verifier);
		Router baseRouter = new Router(getContext());

		Router privateRouter = new Router(getContext());
		// Define the v1 API routes
		defineV1Routes(privateRouter);

		
		String apiPath = "http://localhost:9000/redbox/api/v1";
		//TODO: ...
//		try {
//			JsonSimpleConfig config = new JsonSimpleConfig();
//			String urlBase = config.getString("http://localhost:9000/redbox", "urlBase");
//			apiPath = urlBase + "/api/v1";
//		} catch (IOException e) {
//		}
		Swagger2SpecificationRestlet swagger2SpecificationRestlet = new Swagger2SpecificationRestlet(this);
		swagger2SpecificationRestlet.setBasePath(apiPath);
		swagger2SpecificationRestlet.attach(baseRouter);

		guard.setNext(privateRouter);
		baseRouter.attach(guard);
		return baseRouter;
	}

	

	private void defineV1Routes(Router router) {
		router.attach("/v1/record/{oid}", RecordResource.class);
//		router.attach("/v1/objectmetadata/{oid}", ObjectMetadataResource.class);
//		router.attach("/v1/datastream/{oid}/list", ListDatastreamResource.class);
//		router.attach("/v1/datastream/{oid}", DatastreamResource.class);
//		router.attach("/v1/object/{packageType}", ObjectResource.class);
//		router.attach("/v1/object/{oid}/delete", DeleteObjectResource.class);
//		router.attach("/v1/info", InfoResource.class);
//		router.attach("/v1/search", SearchResource.class);
//		router.attach("/v1/search/{index}", SearchByIndexResource.class);
//		router.attach("/v1/messaging/{messageQueue}", QueueMessageResource.class);
	}
	
	private void defineV2Routes(Router router) {
//		router.attach("/v2/recordmetadata/{oid}", RecordMetadataResource.class);
//		router.attach("/v2/objectmetadata/{oid}", ObjectMetadataResource.class);
//		router.attach("/v2/datastream/{oid}/list", com.googlecode.fascinator.redbox.ws.v2.resources.ListDatastreamResource.class);
//		router.attach("/v2/datastream/{oid}", DatastreamResource.class);
//		router.attach("/v2/object/{packageType}", ObjectResource.class);
//		router.attach("/v2/object/{oid}/delete", DeleteObjectResource.class);
//		router.attach("/v2/info", InfoResource.class);
//		router.attach("/v2/search", SearchResource.class);
//		router.attach("/v2/search/{index}", SearchByIndexResource.class);
//		router.attach("/v2/query", com.googlecode.fascinator.redbox.ws.v2.resources.QueryResource.class);
//		router.attach("/v2/messaging/{messageQueue}", QueueMessageResource.class);
//		router.attach("/v2/harvest/{packageType}", HarvestResource.class);
//		router.attach("/v2/recordmetadata/{oid}/relationships", RecordRelationshipsResource.class);

	}

	@Override
	public String getName() {
		return "Proai Web Service";
	}

	@Override
	public String getAuthor() {
		return "QCIF";
	}

	@Override
	public String getDescription() {
		return "Web Service that allows interaction with Proai storage";
	}
}
