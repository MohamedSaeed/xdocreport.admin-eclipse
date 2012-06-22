package fr.opensagres.xdocreport.admin.eclipse.core;

import fr.opensagres.xdocreport.admin.eclipse.core.internal.RepositoryManager;

public class Platform {

	private static final IRepositoryManager INSTANCE = new RepositoryManager();

	public static IRepositoryManager getRepositoryManager() {
		return INSTANCE;
	}
}
