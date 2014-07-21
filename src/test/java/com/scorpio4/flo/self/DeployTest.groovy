package com.scorpio4.flo.self
import com.scorpio4.iq.vocab.Scorpio4ActiveVocabularies
import com.scorpio4.runtime.MockEngine
import org.junit.Test
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.self
 * @author lee
 * Date  : 13/07/2014
 * Time  : 8:03 PM
 */
public class DeployTest extends GroovyTestCase {

	@Test
	public void testRoute() throws Exception {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/flo/self/deploy.n3", getClass().getClassLoader());

		Scorpio4ActiveVocabularies activeVocabularies = new Scorpio4ActiveVocabularies(engine);
		activeVocabularies.startAndWait();
//		def deployed = activeVocabularies.activate("direct:test", null);
//		println "Deployed: "+deployed
		Thread.sleep(10000);

		activeVocabularies.stop()
		engine.stop()
	}
}
