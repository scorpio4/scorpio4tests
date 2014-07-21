package com.scorpio4.vendor.sesame.asq

import com.scorpio4.asq.sparql.SelectSPARQL

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.vendor.sesame.asq
 * User  : lee
 * Date  : 19/07/2014
 * Time  : 11:51 PM
 *
 *
 */
class SPARQL2ASQTest extends GroovyTestCase {

	void test2ASQ() {
		def sparqlasq = new SPARQL2ASQ("urn:test", """
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT ?this ?label WHERE { ?this rdfs:label ?label. OPTIONAL { ?this rdfs:comment ?comment. } }
""");
		println "ASQ: "+sparqlasq;

		SelectSPARQL sparql = new SelectSPARQL(sparqlasq.getASQ());
		println "SPARQL: "+sparql;
	}
}
