package com.scorpio4.curate.rdf

import com.scorpio4.fact.stream.N3IOStream

import static com.scorpio4.util.io.JDBCHelper.connect

/**
 * Created by lee on 12/09/2014.
 */
class SQLDataCuratorTest extends GroovyTestCase {
	String jdbcURI = "jdbc:jtds:sqlserver://203.29.76.25:1433/ShippingDelivery";

	void tearDown() {

	}

	void testCurate() {
		def connection = connect(jdbcURI, "sa", "")
		SQLDataCurator sqlCurator = new SQLDataCurator(jdbcURI);
		sqlCurator.setCatalog("ShippingDelivery")
		def dir = new File("scorpio4/scorpio4tests/src/test/resources/scorpio4/curate/sql/");
		dir.mkdirs();
		def file = new File(dir, sqlCurator.getCatalog()+".dump.n3")
		println "PATH: ${file.getAbsolutePath()}";
		def stream = new N3IOStream(new PrintWriter(new FileWriter(file)));
		stream.prolog(sqlCurator.getCatalog());
		sqlCurator.curate(stream, connection);
		stream.close();
		println stream;
	}

	void testCurate1() {

	}

	void testCurate2() {

	}

	void testCanCurate() {

	}
}
