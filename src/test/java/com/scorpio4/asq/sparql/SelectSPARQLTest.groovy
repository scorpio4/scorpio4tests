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
class SelectSPARQLTest {
    String baseURI = "urn:test:factcore:fact:query:sparql:ASQ:";

    @Test
    void testASQ() {
        ASQ asq = new BasicASQ(baseURI);
        asq.where("?this", CORE.A, CORE.RDFS+"Class");
        assert asq.getPatterns() != null;
        assert asq.getPatterns().size() == 1;
        assert asq.getBindings().size() == 1;
    }

    @Test
    void testSimpleWhere() {
        ASQ asq = new BasicASQ(baseURI);
        asq.where("?this", CORE.A, CORE.RDFS+"Class");
        assert asq.getBindings().size() == 1;

        println "ASQ: ${asq}"
	    SelectSPARQL sparql = new SelectSPARQL(asq);
	    assert sparql != null;
	    assert sparql.toString().contains("SELECT")
	    println "SPARQL: "+sparql;
    }

    @Test
    void testOptional() {
        ASQ asq = new BasicASQ(baseURI);
        asq.where("?this", CORE.A, CORE.RDFS+"Class");
        asq.where("?this", CORE.LABEL.stringValue(), "?label", true);
        assert asq.getBindings().size() == 2;
        println "ASQ: ${asq}"
	    SelectSPARQL sparql = new SelectSPARQL(asq);
	    assert sparql != null;
	    assert sparql.toString().contains("SELECT")
	    assert sparql.toString().contains("OPTIONAL")
	    println "SPARQL: "+sparql;
    }

    @Test
    void testFunction() {
        ASQ asq = new BasicASQ(baseURI);
        asq.where("?this", CORE.A, CORE.RDFS+"Class");
        asq.where("?this", CORE.LABEL.stringValue(), "?label", true);
        asq.where("?x", "!BOUND(?label)", "?bound");
//        domain.orderBy(["?this", "?label"]);
        assert asq.getBindings().size() == 3;
        assert asq.getFunctions().size() == 1;

	    SelectSPARQL sparql = new SelectSPARQL(asq);
	    assert sparql != null;
	    assert sparql.toString().contains("SELECT")
	    assert sparql.toString().contains("BOUND")
	    println "SPARQL: "+sparql;
    }

    @Test
    void testFilter() {
        ASQ asq = new BasicASQ(baseURI);
        asq.where("?this", CORE.A, CORE.RDFS+"Class");
        asq.where("?this", CORE.LABEL.stringValue(), "?label", true);
        asq.filter("!BOUND(?label)");
//        domain.orderBy(["?this", "?label"]);
        assert asq.getBindings().size() == 2;
        assert asq.getFunctions().size() == 1;

        SelectSPARQL sparql = new SelectSPARQL(asq);
	    assert sparql != null;
	    assert sparql.toString().contains("SELECT")
	    assert sparql.toString().contains("BOUND")
        println "SPARQL: "+sparql;
    }


    @Test
    void testConstruct() {
        ASQ asq = new BasicASQ(baseURI);
        asq.where("?this", CORE.A, CORE.RDFS+"Class");
        asq.optional("?this", CORE.LABEL.stringValue(), "?label");
        asq.filter("!BOUND(?label)");
        assert asq.getBindings().size() == 2;
        assert asq.getFunctions().size() == 1;
        ASQ learn = new BasicASQ(baseURI);
        learn.where("?this", CORE.LABEL.stringValue(), "?this");

        SelectSPARQL sparql = new ConstructSPARQL(asq, learn);
	    assert sparql != null;
	    assert sparql.toString().contains("CONSTRUCT")
        println "SPARQL: "+sparql;
    }

}
