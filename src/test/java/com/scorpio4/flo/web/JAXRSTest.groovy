package com.scorpio4.flo.web
import com.scorpio4.iq.vocab.Scorpio4ActiveVocabularies
import com.scorpio4.runtime.MockEngine
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.flo.self
 * @author lee
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
		activeVocabularies.startAndWait();

		activeVocabularies.stop()
		engine.stop()
	}

	public void testRequestResponse() {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/flo/web/jaxrs.n3", getClass().getClassLoader());

		Scorpio4ActiveVocabularies activeVocabularies = new Scorpio4ActiveVocabularies(engine);
		activeVocabularies.startAndWait();

		def activated = activeVocabularies.activate("urn:scorpio4tests:flo:web:jaxrs:test", null, String.class);
		assert activated!=null;
		assert activated == "received: hello: greetings";

		activeVocabularies.stop()
		engine.stop()
	}
}
