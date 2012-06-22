package fr.opensagres.xdocreport.admin.eclipse.core;

import fr.opensagres.xdocreport.remoting.resources.services.ResourcesService;

public interface IRepositoryManager {

	Repository[] getRepositories();

	ResourcesService getResourcesService(Repository repository);

	void saveRepository(Repository repository);

	void removeRepository(Repository repository);
	
}
