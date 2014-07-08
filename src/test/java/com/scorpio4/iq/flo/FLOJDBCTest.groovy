package com.scorpio4.iq
import com.scorpio4.deploy.Scorpio4SesameDeployer
import com.scorpio4.runtime.MockEngine
/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.iq
 * User  : lee
 * Date  : 17/06/2014
 * Time  : 9:44 PM
 *
 *
 */
class FLOJDBCTest extends GroovyTestCase {

	void testSimpleFLO() {
		MockEngine engine = new MockEngine();
		Scorpio4SesameDeployer sesameDeployer = new Scorpio4SesameDeployer(engine.getFactSpace());
		sesameDeployer.classpath("scorpio4/flo/jdbc.n3");
		engine.reboot();

		SpringyBeansVocabulary beans = new SpringyBeansVocabulary(engine);
		FLOVocabulary flo = new FLOVocabulary(engine);
		flo.start();
		Thread.sleep(20000)
	}

}
