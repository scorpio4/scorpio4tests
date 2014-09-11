package com.scorpio4.iq.vocab

/**
 * Created by lee on 6/09/2014.
 */
class MapReduceActiveVocabularyTest extends GroovyTestCase {

	void testUriToClass() {

	}

	void testRegister() {
		def vocabulary = new MapReduceActiveVocabulary();
		def jobURI = "";
		def mapperBeanURI = "";
		def reduceBeanURI = "";
		def inputBeanURI = "";
		def outputBeanURI = "";
		def typeBeanURI = "";
		def executor = vocabulary.register(jobURI, mapperBeanURI, reduceBeanURI, inputBeanURI, outputBeanURI, typeBeanURI);
		assert executor!=null;
		assert executor.getInputBeanURI()==inputBeanURI;
		assert executor.getOutputBeanURI()==outputBeanURI;
		def executor2 = vocabulary.activate(jobURI, "test");
		assert executor2 == executor;
	}

	void testActivate() {
		def vocabulary = new MapReduceActiveVocabulary();

		vocabulary.register()
	}
}
