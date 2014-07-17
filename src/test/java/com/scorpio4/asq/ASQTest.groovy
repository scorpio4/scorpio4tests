package com.scorpio4.asq
import com.scorpio4.asq.parser.ASQ4Sesame
import com.scorpio4.asq.sparql.ConstructSPARQL
import com.scorpio4.asq.sparql.SelectSPARQL
import com.scorpio4.iq.vocab.ASQVocabulary
import com.scorpio4.runtime.MockEngine
import com.scorpio4.vendor.sesame.util.SesameHelper
import com.scorpio4.vocab.COMMON

import static com.scorpio4.vendor.sesame.util.SesameHelper.*

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.asq
 * User  : lee
 * Date  : 16/07/2014
 * Time  : 6:27 PM
 *
 *
 */
class ASQTest extends GroovyTestCase {
	String simpleURI = "urn:scorpio4tests:asq:simple:";
	String inferURI = "urn:scorpio4tests:asq:infer:";
	String learnURI = "urn:scorpio4tests:asq:infer:test";

	void testSimpleSelect() {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/asq/simple.n3");
		def connection = engine.getRepository().getConnection();

		ASQ4Sesame sesame2ASQ = new ASQ4Sesame(connection,simpleURI);
		assert  sesame2ASQ.getASQ().getIdentity() == simpleURI;
		def asq = sesame2ASQ.getASQ();
		assert asq!=null;

		SelectSPARQL sparql = new SelectSPARQL(asq);
		assert sparql.toString()!=null;
		def maps = toMapCollection(connection, asq);
		assert maps!=null;
		assert maps.size()>0;
		print "SPARQL: "+sparql;
		connection.close();
	}

	void testSimpleInference() {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/asq/simple.n3");
		engine.provision("scorpio4/asq/infer.n3");
		def connection = engine.getRepository().getConnection();

		ASQ4Sesame simpleASQ = new ASQ4Sesame(connection,simpleURI);
		assert  simpleASQ.getASQ().getIdentity() == simpleURI;

		ASQ4Sesame inferASQ = new ASQ4Sesame(connection,inferURI);
		assert  inferASQ.getASQ().getIdentity() == inferURI;

		ConstructSPARQL sparql = new ConstructSPARQL(inferASQ.getASQ(), simpleASQ.getASQ());
		assert sparql.toString()!=null;
		connection.close();
	}

	void testSPARQL() {
		MockEngine engine = new MockEngine();
//		engine.provision("com/scorpio4/vocab/asq/ASQ.rdfs.n3");
		engine.provision("scorpio4/asq/simple.n3");
		engine.provision("scorpio4/asq/infer.n3");
		ASQVocabulary asq = new ASQVocabulary(engine);
		def sparql = asq.toSPARQL(simpleURI, null);
		assert sparql!=null;
		assert sparql instanceof SelectSPARQL

		sparql = asq.toSPARQL(learnURI, null);
		assert sparql!=null;
		assert sparql instanceof ConstructSPARQL
	}

	void testGeneratedAssets() {
		MockEngine engine = new MockEngine();
//		engine.provision("com/scorpio4/vocab/asq/ASQ.rdfs.n3");
		engine.provision("scorpio4/asq/simple.n3");
		engine.provision("scorpio4/asq/infer.n3");
		ASQVocabulary asq = new ASQVocabulary(engine);
		asq.start()
		assert asq.isActive()

		def assets = engine.getAssetRegister();
		def asset = assets.getAsset( simpleURI, null);
		assert asset!=null;
		assert asset.getIdentity() == simpleURI;
		assert asset.getMimeType() == COMMON.MIME_SPARQL;
		assert asset.getContent().toString().contains("SELECT");

		asset = assets.getAsset( inferURI, null);
		assert asset!=null;
		assert asset.getIdentity() == inferURI;
		assert asset.getMimeType() == COMMON.MIME_SPARQL;
		assert asset.getContent().toString().contains("SELECT");

		asset = assets.getAsset( learnURI, null);
		assert asset!=null;
		assert asset.getIdentity() == learnURI;
		assert asset.getMimeType() == COMMON.MIME_SPARQL;
		assert asset.getContent().toString().contains("CONSTRUCT");

	}

	void testActiveSelect() {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/asq/simple.n3");
		ASQVocabulary asq = new ASQVocabulary(engine);
		assert !asq.isActive()
		def connection = engine.getRepository().getConnection();
		SelectSPARQL sparql = asq.activate(simpleURI, null);
		assert sparql!=null;
		assert sparql.toString().contains("SELECT");
		def list = SesameHelper.toMapCollection(connection, sparql.toString());
		assert list!=null;
		assert list.size()>0;
		connection.close();
	}

	void testActiveInfer() {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/asq/simple.n3");
		engine.provision("scorpio4/asq/infer.n3");
		ASQVocabulary asq = new ASQVocabulary(engine);
		assert !asq.isActive()
		def connection = engine.getRepository().getConnection();
		SelectSPARQL sparql = asq.activate(learnURI, null);
		assert sparql!=null;
		assert sparql.toString().contains("SELECT");
		def list = SesameHelper.toMapCollection(connection, sparql.toString());
		assert list!=null;
		assert list.size()>0;
		connection.close();
	}
}
