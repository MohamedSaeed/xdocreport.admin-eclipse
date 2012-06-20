package fr.opensagres.xdocreport.admin.eclipse.ui.editors.repository;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import fr.opensagres.xdocreport.admin.domain.Repository;

public class RepositoryEditorInput  implements IEditorInput {

	private final Repository repository;
	
	public RepositoryEditorInput(Repository repository) {
		this.repository = repository;
	}
	
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		return repository.getBaseAddress();
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return getName();
	}

	public Repository getRepository() {
		return repository;
	}
}
