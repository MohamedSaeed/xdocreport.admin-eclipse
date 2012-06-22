package fr.opensagres.xdocreport.admin.eclipse.core.internal;

import java.util.ArrayList;
import java.util.List;

import fr.opensagres.xdocreport.admin.eclipse.core.IRepositoryManager;
import fr.opensagres.xdocreport.admin.eclipse.core.Repository;
import fr.opensagres.xdocreport.remoting.resources.services.ResourcesService;
import fr.opensagres.xdocreport.remoting.resources.services.ServiceType;
import fr.opensagres.xdocreport.remoting.resources.services.rest.client.JAXRSResourcesServiceClientFactory;
import fr.opensagres.xdocreport.remoting.resources.services.ws.client.JAXWSResourcesServiceClientFactory;

public class RepositoryManager implements IRepositoryManager {

	private List<Repository> repositories = new ArrayList<Repository>();

	public RepositoryManager() {
		Repository r = new Repository();
		r.setBaseAddress("http://xdocreport.opensagres.cloudbees.net/cxf/");
		r.setServiceType(ServiceType.REST);
		saveRepository(r);
		r = new Repository();
		r.setBaseAddress("http://localhost:8080/xdocreport-webapp/cxf");
		r.setServiceType(ServiceType.REST);
		saveRepository(r);
		r = new Repository();
		r.setBaseAddress("http://localhost:8080/xdocreport-webapp/cxf/resources");
		r.setServiceType(ServiceType.SOAP);
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

		if (ServiceType.SOAP.equals(repository.getServiceType())) {
			return JAXWSResourcesServiceClientFactory.create(baseAddress,
					username, password, connectionTimeout, allowChunking);
		}
		return JAXRSResourcesServiceClientFactory.create(baseAddress, username,
				password, connectionTimeout, allowChunking);
	}

	public void saveRepository(Repository repository) {
		repositories.add(repository);
	}

	public void removeRepository(Repository repository) {
		repositories.remove(repository);
	}
}
