package com.scorpio4.flo.self
import com.scorpio4.iq.vocab.Scorpio4ActiveVocabularies
import com.scorpio4.runtime.MockEngine
import org.openrdf.rio.RDFFormat
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.flo.self
 * @author lee
 * Date  : 14/07/2014
 * Time  : 2:46 AM
 */
public class SPARQLTest extends GroovyTestCase {

//	@Test
	void testTypes() {
		RDFFormat format = RDFFormat.forMIMEType("application/ld+json");
		def register = RDFFormat.register("application/ldjson", format.getDefaultMIMEType(), format.getDefaultFileExtension(), format.getCharset());
		println "FORMAT: ${format}"
		println "REG: ${register}"
	}

//	@Test
	public void XX_testFLO() throws Exception {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/flo/self/sparql.n3", getClass().getClassLoader());

		Scorpio4ActiveVocabularies activeVocabularies = new Scorpio4ActiveVocabularies(engine);
		activeVocabularies.startAndWait();
		def deployed = activeVocabularies.activate("direct:flo:self:sparql", null);
		assert deployed!=null;
		assert deployed.getClass() == String.class;
		println "Deployed: "+deployed
		activeVocabularies.stop()
		engine.stop()

	}
}
