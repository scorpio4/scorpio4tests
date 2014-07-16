package com.scorpio4.flo.web
import com.scorpio4.iq.vocab.Scorpio4ActiveVocabularies
import com.scorpio4.runtime.MockEngine
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.flo.self
 * User  : lee
 * Date  : 14/07/2014
 * Time  : 6:58 PM
 *
 *
 */
class CXFBeanTest extends GroovyTestCase {

	public void testFLO() {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/flo/web/cxfbean.n3", getClass().getClassLoader());


		Scorpio4ActiveVocabularies activeVocabularies = new Scorpio4ActiveVocabularies(engine);

		activeVocabularies.startAndWait();
		Thread.sleep(60000);
	}
}
