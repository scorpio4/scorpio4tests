package com.scorpio4.vendor.sesame


import org.openrdf.repository.manager.RepositoryInfo

/**
 * Scorpio4 (c) 2010-2014
 * @author lee
 * Date: 21/01/13
 * Time: 10:11 PM
 * 
 * This code does something useful
 */
class IQRepositoryManagerIT extends GroovyTestCase {

	void testCoreRepository() {
		RepositoryManager crm = new RepositoryManager( "test", new URL("http://127.0.0.1:8080/openrdf-sesame/"), new File("/opt/scorpio4/Kernel.Internals"));
		println "CRM: "+crm.getIdentity();
		assert crm.getIdentity()=="test";
		crm.shutDown();
	}

	void testLocalRepository() {
		RepositoryManager crm = new RepositoryManager("test", new URL("http://127.0.0.1:8080/openrdf-sesame/"), new File("/opt/scorpio4/Kernel.Internals"));
		println "CRM: "+crm.getIdentity();
		List<RepositoryInfo> repos =  crm.getAllRepositoryInfos();
		for(int i=0;i<repos.size();i++ ) {
			RepositoryInfo repositoryInfo = repos.get(i);
			println "-> "+repositoryInfo.getId()+" @ "+repositoryInfo.getLocation();
		}
		crm.shutDown();
	}

}
