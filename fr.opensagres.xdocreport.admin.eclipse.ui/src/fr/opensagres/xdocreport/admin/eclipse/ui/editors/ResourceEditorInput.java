package fr.opensagres.xdocreport.admin.eclipse.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import fr.opensagres.xdocreport.remoting.resources.domain.Resource;

public class ResourceEditorInput  implements IEditorInput {

	private final Resource resource;
	
	public ResourceEditorInput(Resource resource) {
		this.resource = resource;
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
		return resource.getName();
	}

	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getToolTipText() {
		return getName();
	}

	public Resource getResource() {
		return resource;
	}
}
