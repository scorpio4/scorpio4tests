package com.scorpio4.iq.vocab;

import com.scorpio4.runtime.MockEngine;
import com.scorpio4.vocab.COMMONS;
import groovy.util.GroovyTestCase;

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.iq.vocab
 * @author lee
 * Date  : 9/07/2014
 * Time  : 8:54 PM
 */
public class HydraVocabularyTest extends GroovyTestCase {

	public void TODO_testVocabs() throws Exception {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/hydra/mock_api.n3", HydraVocabulary.class.getClassLoader());
		engine.provision("scorpio4/vocabs/w3c/hydra/hydra-cg.n3", COMMONS.class.getClassLoader());
		HydraVocabulary hydra = new HydraVocabulary(engine);
		assert !hydra.isActive();
		hydra.start();
		assert hydra.isActive();

		hydra.stop();
		engine.stop();
	}

}
