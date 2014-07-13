package com.scorpio4.iq.vocab;

import com.scorpio4.runtime.MockEngine;
import com.scorpio4.vocab.COMMON;
import groovy.util.GroovyTestCase;

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.iq.vocab
 * User  : lee
 * Date  : 9/07/2014
 * Time  : 8:54 PM
 */
public class HydraVocabularyTest extends GroovyTestCase {

	public void testVocabs() throws Exception {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/hydra/mock_api.n3", HydraVocabulary.class.getClassLoader());
		engine.provision("scorpio4/vocabs/hydra/hydra-cg.n3", COMMON.class.getClassLoader());
		HydraVocabulary hydra = new HydraVocabulary();
		hydra.boot(engine);
	}

}
