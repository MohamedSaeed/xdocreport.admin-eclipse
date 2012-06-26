package fr.opensagres.xdocreport.admin.eclipse.core;

public class DomainHelper {

	public static Repository clone(Repository repository) {
		Repository newRepository = new Repository();
		copy(repository, newRepository);
		return newRepository;
	}

	public static void copy(Repository src, Repository dest) {
		dest.setId(src.getId());
		dest.setBaseAddress(src.getBaseAddress());
		dest.setUsername(src.getUsername());
		dest.setPassword(src.getPassword());
		dest.setAllowChunking(src.getAllowChunking());
		dest.setConnectionTimeout((src.getConnectionTimeout()));
		dest.setServiceType(src.getServiceType());
	}
}
