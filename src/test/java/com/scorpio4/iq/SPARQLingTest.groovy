package com.scorpio4.iq
import com.scorpio4.assets.Asset
import com.scorpio4.iq.exec.SPARQLing
import com.scorpio4.vendor.sesame.store.MemoryRDFSRepository
import com.scorpio4.vocab.COMMON
/**
 * Scorpio4 (c) 2014
 * Module: com.scorpio4.iq
 * @author lee
 * Date  : 17/06/2014
 * Time  : 10:22 PM
 *
 *
 */
class SPARQLingTest extends GroovyTestCase {

    void testWith() {
	    def repository = new MemoryRDFSRepository();
//	    MockEngine.provision(repository, "", getClass().getClassLoader());
	    def connection = repository.getConnection()
        def sparqling = new SPARQLing(connection);
	    Asset query = new Asset("urn:test", "SELECT DISTINCT ?o WHERE {?s ?p ?o}", COMMON.MIME_SPARQL);
        def future = sparqling.execute(query, ["hello": "world"])
        assert future!=null;
        def result = future.get();
        assert result!=null;
        assert result instanceof Collection<Map>;
        assert !result.isEmpty();

	    connection.close();
    }
}
