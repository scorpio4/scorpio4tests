package com.scorpio4.flo.self
import com.scorpio4.iq.vocab.ActiveBeansVocabulary
import com.scorpio4.iq.vocab.ActiveFLOVocabulary
import com.scorpio4.runtime.MockEngine
import org.junit.Test
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.flo.self
 * @author lee
 * Date  : 15/07/2014
 * Time  : 9:18 AM
 *
 *
 */
class AssetTest extends GroovyTestCase {

	@Test
	public void testRoute() throws Exception {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/flo/self/asset.n3", getClass().getClassLoader());

		def springBeans = new ActiveBeansVocabulary(engine);
		assert !springBeans.isActive();
		springBeans.start();
		assert springBeans.isActive();

		def flo = new ActiveFLOVocabulary(engine);
		assert !flo.isActive();
		flo.start();
		assert flo.isActive();

		assert flo.getCamelContext()!=null;
		flo.start();
		def deployed = flo.activate("direct:test", null);
		println "Deployed: "+deployed

		flo.stop()
		springBeans.stop()
		engine.stop()
	}
}
