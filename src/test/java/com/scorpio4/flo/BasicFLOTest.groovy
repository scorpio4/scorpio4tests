package com.scorpio4.flo
import com.scorpio4.iq.vocab.ActiveBeansVocabulary
import com.scorpio4.iq.vocab.ActiveFLOVocabulary
import com.scorpio4.runtime.MockEngine
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.flo
 * User  : lee
 * Date  : 20/07/2014
 * Time  : 11:01 PM
 */
public class BasicFLOTest extends GroovyTestCase {


	void testActivation() throws Exception {
		MockEngine engine = new MockEngine();
		ActiveBeansVocabulary beans = new ActiveBeansVocabulary(engine);
		beans.start();
		assert beans.isActive();

		ActiveFLOVocabulary flo = new ActiveFLOVocabulary(engine);
		flo.start();
		assert flo.isActive();

		flo.stop()
		beans.stop()
		engine.stop();
	}

}
