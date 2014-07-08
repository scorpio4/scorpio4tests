package com.scorpio4.runtime;

import com.scorpio4.assets.AssetRegister;
import com.scorpio4.assets.AssetRegisters;
import com.scorpio4.fact.FactSpace;
import com.scorpio4.vendor.sesame.MockRepositoryManager;
import com.scorpio4.vendor.sesame.store.MemoryRDFSRepository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.repository.sail.config.RepositoryResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

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
	FactSpace factSpace;
	ApplicationContext context;
	AssetRegisters assetRegisters;
	Map config = new HashMap();

	public MockEngine() throws RepositoryException {
		repository = new MemoryRDFSRepository();
		SailRepositoryConnection connection = repository.getConnection();
		factSpace = new FactSpace(connection, identity);
		assetRegisters = new AssetRegisters(connection);
		repositoryResolver = new MockRepositoryManager(repository);
		context = new GenericApplicationContext();
	}

	@Override
	public FactSpace getFactSpace() {
		return factSpace;
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
	public Map getConfig() {
		return config;
	}

	@Override
	public void reboot() throws Exception {

	}

	@Override
	public String getIdentity() {
		return identity;
	}
}
