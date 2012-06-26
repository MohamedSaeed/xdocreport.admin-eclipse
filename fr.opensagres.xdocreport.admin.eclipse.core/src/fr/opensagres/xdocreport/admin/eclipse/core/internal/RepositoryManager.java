package fr.opensagres.xdocreport.admin.eclipse.core.internal;

import java.util.HashMap;
import java.util.Map;

import fr.opensagres.xdocreport.admin.eclipse.core.DomainHelper;
import fr.opensagres.xdocreport.admin.eclipse.core.IRepositoryManager;
import fr.opensagres.xdocreport.admin.eclipse.core.Repository;
import fr.opensagres.xdocreport.remoting.resources.services.ResourcesService;
import fr.opensagres.xdocreport.remoting.resources.services.ServiceType;
import fr.opensagres.xdocreport.remoting.resources.services.client.jaxrs.JAXRSResourcesServiceClientFactory;
import fr.opensagres.xdocreport.remoting.resources.services.client.jaxws.JAXWSResourcesServiceClientFactory;

public class RepositoryManager implements IRepositoryManager {

	private Map<Long, Repository> repositories = new HashMap<Long, Repository>();

	public RepositoryManager() {
		Repository r = new Repository();
		r.setBaseAddress("http://xdocreport.opensagres.cloudbees.net/cxf/");
		r.setServiceType(ServiceType.JAXRS);
		saveRepository(r);
		r = new Repository();
		r.setBaseAddress("http://localhost:8080/xdocreport-webapp/cxf");
		r.setServiceType(ServiceType.JAXRS);
		saveRepository(r);
		r = new Repository();
		r.setBaseAddress("http://localhost:8080/xdocreport-webapp/cxf/resources");
		r.setServiceType(ServiceType.JAXWS);
		saveRepository(r);
	}

	public Repository[] getRepositories() {
		return repositories.values().toArray(new Repository[0]);
	}

	public ResourcesService getResourcesService(Repository repository) {
		String baseAddress = repository.getBaseAddress();
		String username = repository.getUsername();
		String password = repository.getPassword();
		Long connectionTimeout = repository.getConnectionTimeout();
		Boolean allowChunking = repository.getAllowChunking();

		if (ServiceType.JAXWS.equals(repository.getServiceType())) {
			return JAXWSResourcesServiceClientFactory.create(baseAddress,
					username, password, connectionTimeout, allowChunking);
		}
		return JAXRSResourcesServiceClientFactory.create(baseAddress, username,
				password, connectionTimeout, allowChunking);
	}

	public Repository saveRepository(Repository repository) {
		if (repository.getId() == null) {
			repository.setId(new Long(repositories.values().size()));
			repositories.put(repository.getId(), repository);
			return repository;
		}
		Repository r = repositories.get(repository.getId());
		DomainHelper.copy(repository, r);
		return r;
	}

	public void removeRepository(Repository repository) {
		repositories.remove(repository);
	}
}
