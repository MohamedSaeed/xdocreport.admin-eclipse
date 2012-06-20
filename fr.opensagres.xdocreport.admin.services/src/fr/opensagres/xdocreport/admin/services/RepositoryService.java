package fr.opensagres.xdocreport.admin.services;

import fr.opensagres.xdocreport.admin.domain.Repository;
import fr.opensagres.xdocreport.remoting.resources.services.ResourcesService;

public interface RepositoryService {

	Repository[] getRepositories();

	ResourcesService getResourcesService(Repository repository);

	void saveRepository(Repository repository);
	
}
