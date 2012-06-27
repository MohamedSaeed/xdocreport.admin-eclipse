package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources;

import fr.opensagres.eclipse.forms.editor.ModelEditorInput;
import fr.opensagres.xdocreport.admin.eclipse.core.Repository;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;

public class ResourceEditorInput extends ModelEditorInput<Resource> {

	private final Repository repository;

	public ResourceEditorInput(Repository repository, Resource resource) {
		super(resource);
		this.repository = repository;
	}

	public String getName() {
		return getModel().getName();
	}

	public String getToolTipText() {
		return getName();
	}

	public Repository getRepository() {
		return repository;
	}
}
