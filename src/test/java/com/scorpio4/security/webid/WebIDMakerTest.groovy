package com.scorpio4.security.webid
import com.scorpio4.runtime.MockEngine
import com.scorpio4.vendor.sesame.crud.SesameCRUD
import org.bouncycastle.jce.X509Principal

import java.security.Key
import java.security.cert.X509Certificate
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.security.webid
 * User  : lee
 * Date  : 25/07/2014
 * Time  : 5:17 PM
 *
 *
 */
class WebIDMakerTest extends GroovyTestCase {
	File issuerKeyFile = new File("scorpio4/scorpio4tests/src/test/resources/scorpio4/acl/webid/issuerKeyStore.ks");
	File keyFile = new File("scorpio4/scorpio4tests/src/test/resources/scorpio4/acl/webid/keyStore.ks");
	String webId = "https://scorpio4demo.com/acl/webid/demo"

	void TODO_testGenerateSelf() {
		String spkac = "b06aca85d3713133675ed2f8a67ad9449095cd1bbde8d6609c9b487a7dc4e0fcd66557b3b18a481d50d01d117a6e7f1f4389eb0b24e22c757727c26ee2d051e80023bb000f607aea4dcf54d7f3c3e8870fe4734473b5dae2b1be434cbd54fc430ad89c194a91e5a68e239dea3e86741200ef150f140bebd3502151f66fb44bf1";
		def subjectAttributes = subjectDN();
		def issuer = new WebIDSelfCertIssuer();
		X509Certificate certificate = issuer.selfCertificate(spkac, subjectAttributes);
		assert certificate!=null;
	}

	void testGenerateKeyPair() {
		WebIDMaker webIDGenerator = new WebIDMaker();
		def keyPair = webIDGenerator.generateKeyPair();
		def privateKey = (RSAPrivateKey)keyPair.getPrivate();
		privateKey.getPrivateExponent();
		println "EXP: "+privateKey.getPrivateExponent();
		println "PRIVATE: "+privateKey;
		def publicKey = (RSAPublicKey)keyPair.getPublic();
		println "EXP: "+publicKey.getPublicExponent();
		println "PRIVATE: "+privateKey;

		println "PUBLIC: "+publicKey;
	}

	void testGenerateCertificate() {
		String webId = "https://scorpio4demo.com/security/webid/demo/testGenerateCertificate"
		WebIDMaker webIDMaker = new WebIDMaker();
		def keyPair = webIDMaker.generateKeyPair();
		def _subjectDN = subjectDN();
		def certificate = webIDMaker.selfCertificate(keyPair, webId, _subjectDN);
		println "CERT:"+certificate;
	}

	void testVerifyCertification() {
		def modulus= "adc05e6727915510dcb84f821b6670a43b58c324eb40748f9c3f98bcc24d40c3ba8ba3110e3cfa926b60be95d7beb06b77b401525a54ea88893738bfd1fd86a4c5d85394b06a1f3179b14e55c029c77da2266ecf5231d3fef7bf0be1e041bb65c1e2b3a35b074cff49ef1b02b5c69aa60222f4988c03f5c4c0597f7f5bb23255";
		def exponent = 10001;
		def webIDGenerator = new WebIDMaker();
		def publicKey = webIDGenerator.getPublicKey(modulus, ""+exponent);

		modulus = webIDGenerator.getWebIDProfile(publicKey);
		def publicKey2 = webIDGenerator.getPublicKey(modulus, ""+exponent);

		assert publicKey.getModulus().equals( publicKey2.getModulus() )
		assert publicKey.getPublicExponent().equals( publicKey2.getPublicExponent() )
		assert publicKey.getAlgorithm().equals( publicKey2.getAlgorithm() )
		assert webIDGenerator.isSameKey(publicKey, publicKey2);
	}

	//	public X509Certificate loadCertificate(String certName, Key key, String password, File file) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {

	void testLoadCertificate() {
		def webIDGenerator = new WebIDMaker();
		def certificate = webIDGenerator.loadCertificate("scorpio4tests", "scorpio4tests", keyFile);
		println certificate;
	}

	void testStoreCertificate() {
		def webIDGenerator = new WebIDMaker();
		def keyPair = webIDGenerator.generateKeyPair();
		Key key = keyPair.getPrivate();
		X509Certificate cert = webIDGenerator.selfCertificate(keyPair, webId, subjectDN());
		String password = "scorpio4tests";
		println "KS: "+keyFile.getAbsolutePath();
		webIDGenerator.storeCertificate("scorpio4tests",  cert, key, password, keyFile);
	}

	void testRepositoryCertificate() {
		MockEngine engine = new MockEngine();
		engine.provision("scorpio4/acl/webid.n3")

		def connection = engine.getRepository().getConnection();
		SesameCRUD crud = new SesameCRUD(connection, engine.getIdentity(), engine.getAssetRegister() );
		def certs = crud.read("scorpio4/acl/certs", [ "this": webId ])

		def webIDGenerator = new WebIDMaker();
		assert certs!=null;
		assert certs.size()==1;
		certs.each {
			assert it.this == webId;
			assert it.modulus;
			assert it.exponent;
			def publicKey = webIDGenerator.getPublicKey(it.modulus, it.exponent);
			assert publicKey;
			assert publicKey.getAlgorithm()=="RSA";
			assert publicKey.getModulus(); // can't check direct equality ... base64 vs big-int
			assert publicKey.getPublicExponent()==it.exponent as int;
			println "webID: "+it+" -> "+publicKey;
		}
	}

	Hashtable subjectDN() {
		Hashtable attrs = new Hashtable();
		attrs.put(X509Principal.CN, "scorpio4tests");
		attrs.put(X509Principal.EmailAddress, "webid@scorpio4demo.com");
		return attrs;
	}

}
