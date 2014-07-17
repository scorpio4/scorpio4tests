package com.scorpio4.asq.sparql

import com.scorpio4.asq.ASQ
import com.scorpio4.asq.core.BasicASQ
import com.scorpio4.curate.CORE
import org.junit.Test
/**
 * FactCore (c) 2013
 * Module: com.factcore.fact.query.sparql
 * User  : lee
 * Date  : 16/10/13
 * Time  : 8:10 PM
 * 
 *
 */
class InsertSPARQLTest {
    String baseURI = "urn:test:factcore:fact:query:sparql:ASQ:";

    @Test
    void testInsert() {
        ASQ asq = new BasicASQ(baseURI);
        asq.where("?this", CORE.A, CORE.RDFS+"Class");
        asq.where("?this", CORE.LABEL.stringValue(), "?label", "xsd:string" );
        assert asq.getBindings().size() == 2;
        assert asq.getFunctions().size() == 0;

        InsertSPARQL sqb = new InsertSPARQL(asq);
        println "SPARQL: "+sqb;
    }

}
