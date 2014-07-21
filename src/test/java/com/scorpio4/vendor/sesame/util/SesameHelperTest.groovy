package com.scorpio4.vendor.sesame.util

import com.scorpio4.vendor.sesame.store.MemoryRDFSRepository
import com.scorpio4.vocab.COMMON

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.vendor.sesame.util
 * @author lee
 * Date  : 15/07/2014
 * Time  : 10:02 AM
 *
 *
 */
class SesameHelperTest extends GroovyTestCase {

	void testDefaultNamespaces() {
		MemoryRDFSRepository memoryRDFSRepository = new MemoryRDFSRepository();
		def connection = memoryRDFSRepository.getConnection();
		SesameHelper.defaultNamespaces(connection);
		def namespace = connection.getNamespace("xsd");
		assert namespace == COMMON.XSD;
		connection.close();
		memoryRDFSRepository.shutDown()
	}

	void testResolvePragma() {
		MemoryRDFSRepository memoryRDFSRepository = new MemoryRDFSRepository();
		def connection = memoryRDFSRepository.getConnection();
		SesameHelper.defaultNamespaces(connection);
		def pragma = SesameHelper.explodePragmas(connection, "@namespaces");
		assert pragma != null;
		assert pragma.contains(COMMON.ACL)
		connection.close();
		memoryRDFSRepository.shutDown()
	}
}
