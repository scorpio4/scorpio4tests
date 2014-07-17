package com.scorpio4.runtime;

import com.scorpio4.assets.AssetRegister;
import com.scorpio4.assets.AssetRegisters;
import com.scorpio4.oops.FactException;
import com.scorpio4.vendor.sesame.MockRepositoryManager;
import com.scorpio4.vendor.sesame.store.MemoryRDFSRepository;
import com.scorpio4.vendor.sesame.util.SesameHelper;
import com.scorpio4.vocab.COMMON;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.repository.sail.config.RepositoryResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * scorpio4-oss (c) 2014
 * Module: com.scorpio4.runtime
 * User  : lee
 * Date  : 7/07/2014
 * Time  : 11:18 PM
 */
public class MockEngine implements ExecutionEnvironment {
	MemoryRDFSRepository repository;
	RepositoryResolver repositoryResolver;
	String identity = "urn:scorpio4tests:";
	ApplicationContext context;
	AssetRegisters assetRegisters;
	Map config = new HashMap();

	public MockEngine() throws RepositoryException {
		repository = new MemoryRDFSRepository();
		SailRepositoryConnection connection = repository.getConnection();
		assetRegisters = new AssetRegisters(connection);
		repositoryResolver = new MockRepositoryManager(repository);
		context = new GenericApplicationContext();
		defaultConfig();
		SesameHelper.defaultNamespaces(connection);
		connection.begin();
		connection.setNamespace("flo", COMMON.CORE+"flo/");
		connection.setNamespace("bean", COMMON.CORE+"bean/");
		connection.setNamespace("asq", COMMON.CORE+"asq/");
		connection.commit();
	}

	private void defaultConfig() {
		config.put("identity", identity);
		config.put("httpPort", 9091);
	}

	@Override
	public ApplicationContext getRegistry() {
		return context;
	}

	@Override
	public AssetRegister getAssetRegister() {
		return assetRegisters;
	}

	@Override
	public RepositoryResolver getRepositoryManager() {
		return repositoryResolver;
	}

	@Override
	public Repository getRepository() {
		return repository;
	}

	@Override
	public Map getConfig() {
		return config;
	}

	@Override
	public void reboot() throws Exception {
	}

	@Override
	public ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	@Override
	public String getIdentity() {
		return identity;
	}

	public void provision(String resourcePath, ClassLoader classLoader) throws RepositoryException, FactException, IOException {
		repository.deploy(resourcePath, classLoader);
	}

	public void provision(String resourcePath) throws RepositoryException, FactException, IOException {
		repository.deploy(resourcePath, getClassLoader());
	}
}
