package com.scorpio4.vendor.camel.component
import com.scorpio4.runtime.MockEngine
import com.scorpio4.vendor.camel.component.any23.Any23Component
import org.apache.any23.rdf.RDFUtils
import org.apache.camel.ProducerTemplate
import org.apache.camel.builder.RouteBuilder
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.vendor.camel.component
 * User  : lee
 * Date  : 23/07/2014
 * Time  : 12:55 AM
 *
 *
 */
class Any23Test extends GroovyTestCase {
	String webPage = "http://www.bbc.co.uk/news/"

	void testURI() {
		def uri = new URI(RDFUtils.fixAbsoluteURI("http://www.bbc.co.uk/news/"));
		uri = new URI(RDFUtils.fixAbsoluteURI("bean://com.test.Test"));
	}

	void testAny23() {
		MockEngine engine = new MockEngine();
		engine.start();

		try {
			new URL(webPage).openStream()
		} catch(UnknownHostException e) {
			println("Offline ... can't test download")
			return;
		}

		def camel = engine.newCamel()
		camel.addComponent("any23", new Any23Component(engine));

		camel.addRoutes(new RouteBuilder() {
			void configure() throws Exception {
				from("direct:any23").to(webPage).to("any23:body").to("file://./temp.test/camel/any23/").end();
			}
		});
		camel.start()

		ProducerTemplate producer = camel.createProducerTemplate();
		Object result = producer.requestBodyAndHeaders("direct:any23", new File("scorpio4/scorpio4tests/src/test/resources/scorpio4/vendor/camel/sesame.n3"), engine.getConfig(), String.class);
		assert result!=null;
		println result;

		camel.stop()
		engine.stop()
	}
}
