package com.scorpio4.vendor.camel

import com.scorpio4.iq.vocab.Scorpio4ActiveVocabularies
import com.scorpio4.runtime.MockEngine;

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.vendor.camel
 * User  : lee
 * Date  : 21/07/2014
 * Time  : 4:38 PM
 */
public class Sesame4Camel extends GroovyTestCase{


	void testSimpleQuery() {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/vendor/camel/sesame.n3");
		engine.start();

		Scorpio4ActiveVocabularies sav = new Scorpio4ActiveVocabularies(engine);
		sav.start();
		assert sav.isActive();

		def done = sav.activate("direct:vendor/camel/sesame", null);
		println "DONE: "+done;
		assert done!=null;

		sav.stop()
		engine.stop()
	}
}
