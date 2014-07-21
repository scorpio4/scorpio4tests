package com.scorpio4.flo.self

import com.scorpio4.iq.vocab.Scorpio4ActiveVocabularies
import com.scorpio4.runtime.MockEngine

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.flo.self
 * @author lee
 * Date  : 15/07/2014
 * Time  : 9:18 AM
 *
 *
 */
class TemplateTest extends GroovyTestCase {

	public void testRoute() throws Exception {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/flo/self/template.n3", getClass().getClassLoader());

		Scorpio4ActiveVocabularies activeVocabularies = new Scorpio4ActiveVocabularies(engine);
		activeVocabularies.startAndWait();
		def activated = activeVocabularies.activate("direct:flo:self:template", null);
		println "Template: "+activated

		activeVocabularies.stop()
		engine.stop();
	}

	public void testRouteCamel() throws Exception {

	}
}
