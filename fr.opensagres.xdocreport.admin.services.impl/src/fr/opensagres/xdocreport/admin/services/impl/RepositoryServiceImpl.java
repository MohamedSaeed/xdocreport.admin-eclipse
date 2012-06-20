package fr.opensagres.xdocreport.admin.services.impl;

import java.util.ArrayList;
import java.util.List;

import fr.opensagres.xdocreport.admin.domain.Repository;
import fr.opensagres.xdocreport.admin.services.RepositoryService;
import fr.opensagres.xdocreport.remoting.resources.services.ResourcesService;
import fr.opensagres.xdocreport.remoting.resources.services.rest.client.JAXRSResourcesServiceClientFactory;

public class RepositoryServiceImpl implements RepositoryService {

	private List<Repository> repositories = new ArrayList<Repository>();
	
	public RepositoryServiceImpl() {
		Repository r = new Repository();
		r.setBaseAddress("http://xdocreport.opensagres.cloudbees.net/cxf/");
		saveRepository(r);
	}
	
	public Repository[] getRepositories() {
		return repositories.toArray(new Repository[0]);
	}

	public ResourcesService getResourcesService(Repository repository) {
		String baseAddress = repository.getBaseAddress();
		String username = repository.getUsername();
		String password = repository.getPassword();
		Long connectionTimeout = repository.getConnectionTimeout();
		Boolean allowChunking = repository.getAllowChunking();
		return JAXRSResourcesServiceClientFactory.create(baseAddress, username,
				password, connectionTimeout, allowChunking);
		// return null;
	}
	
	public void saveRepository(Repository repository) {
		repositories.add(repository);
	}
}
