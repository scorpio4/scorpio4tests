package com.scorpio4.flo

import com.scorpio4.iq.vocab.Scorpio4ActiveVocabularies
import com.scorpio4.runtime.MockEngine
import org.junit.Test

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.flo
 * User  : lee
 * Date  : 16/07/2014
 * Time  : 9:09 AM
 *
 *
 */
class PropertiesTest {

	@Test
	public void testFLO() throws Exception {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/flo/properties.n3", getClass().getClassLoader());

		Scorpio4ActiveVocabularies activeVocabularies = new Scorpio4ActiveVocabularies(engine);
		activeVocabularies.startAndWait();
		activeVocabularies.activate("direct:flo:properties", null);
		Thread.sleep(10000);
	}

}
