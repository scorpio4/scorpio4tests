package com.scorpio4.asq.sparql

import com.scorpio4.asq.ASQ
import com.scorpio4.asq.core.BasicASQ
import com.scorpio4.curate.CORE
import org.junit.Test

/**
 * Scorpio4 (c) 2013
 * @author lee
 * Date  : 11/01/2014
 * Time  : 11:28 PM
 * 
 *
 */
class UpdateSPARQLTest {

    String baseURI = "urn:test:scorpio4:fact:query:sparql:ASQ:";

    @Test
    void testUpdate() {
        ASQ asq = new BasicASQ(baseURI);
        asq.where("?this", CORE.A, CORE.RDFS+"Class");
        asq.optional("?this", CORE.LABEL.stringValue(), "?label", "xsd:string" );
        assert asq.getBindings().size() == 2;
        assert asq.getFunctions().size() == 0;

        UpdateSPARQL sqb = new UpdateSPARQL(asq);
        println "SPARQL: "+sqb;
    }
}
