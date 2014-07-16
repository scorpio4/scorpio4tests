package com.scorpio4.flo.web
import com.scorpio4.iq.vocab.Scorpio4ActiveVocabularies
import com.scorpio4.runtime.MockEngine
import org.apache.cxf.jaxrs.JAXRSServiceImpl

import javax.xml.namespace.QName

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.flo.self
 * User  : lee
 * Date  : 14/07/2014
 * Time  : 6:58 PM
 *
 *
 */
class JAXRSTest extends GroovyTestCase {

	public void testRoute() {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/flo/web/jaxrs.n3", getClass().getClassLoader());


		Scorpio4ActiveVocabularies activeVocabularies = new Scorpio4ActiveVocabularies(engine);

		JAXRSServiceImpl service = new JAXRSServiceImpl("http://localhost:9090", new QName(engine.getIdentity()));
		activeVocabularies.getCamelContext().addService(service);

		activeVocabularies.startAndWait();
		Thread.sleep(10000);
	}
}
