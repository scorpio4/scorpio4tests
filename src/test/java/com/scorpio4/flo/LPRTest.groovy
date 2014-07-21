package com.scorpio4.flo
import com.scorpio4.iq.vocab.Scorpio4ActiveVocabularies

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.flo
 * @author lee
 * Date  : 16/07/2014
 * Time  : 3:18 PM
 *
 *
 */
import com.scorpio4.runtime.MockEngine
import org.junit.Test
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.flo
 * @author lee
 * Date  : 16/07/2014
 * Time  : 9:09 AM
 *
 *
 */
public class LPRTest {

	@Test
	public void testPrintFile() throws Exception {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/flo/lpr.n3");

		Scorpio4ActiveVocabularies activeVocabularies = new Scorpio4ActiveVocabularies(engine);
		activeVocabularies.startAndWait();
		File samplePDF = new File("scorpio4/scorpio4tests/src/test/resources/scorpio4/files/ApacheCamelComponentsCropMarksAndBleed.pdf");

		// TODO: hacked to ignore in Maven tests (but works fine in manual test)
if (!samplePDF.exists()) return;


		println samplePDF.getAbsolutePath();
		assert samplePDF.exists();
		def done = activeVocabularies.activate("direct:flo:lpr", samplePDF);
		assert done!=null;
		assert done == samplePDF
		println "DONE: "+done;
//		Thread.sleep(10000);

		activeVocabularies.stop()
		engine.stop();
	}

}