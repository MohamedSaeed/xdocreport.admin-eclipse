package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import fr.opensagres.xdocreport.admin.eclipse.core.IRepositoryManager;
import fr.opensagres.xdocreport.remoting.resources.domain.ResourceType;

public abstract class ResourceEditor extends FormEditor {

	private final ResourceType resourceType;

	public ResourceEditor(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	private IRepositoryManager repositoryService;

	public void setRepositoryService(IRepositoryManager repositoryService) {
		this.repositoryService = repositoryService;
	}

	@Override
	protected void addPages() {
		try {
			super.addPage(new OverviewPage(this, resourceType));
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

}
