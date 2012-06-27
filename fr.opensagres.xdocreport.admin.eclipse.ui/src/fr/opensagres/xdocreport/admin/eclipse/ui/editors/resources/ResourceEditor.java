package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources;

import org.eclipse.ui.PartInitException;

import fr.opensagres.eclipse.forms.editor.ModelFormEditor;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;
import fr.opensagres.xdocreport.remoting.resources.domain.ResourceHelper;
import fr.opensagres.xdocreport.remoting.resources.domain.ResourceType;

public abstract class ResourceEditor<EditorInput extends ResourceEditorInput, Model extends Resource>
		extends ModelFormEditor<EditorInput, Model> {

	private final ResourceType resourceType;

	public ResourceEditor(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	@Override
	protected void doAddPages() {
		try {
			super.addPage(new OverviewPage(this, resourceType));
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Resource onLoad(ResourceEditorInput input) {
		Resource resource = input.getModel();
		return resource;
	}
	
	public ResourceType getResourceType() {
		return resourceType;
	}

}
