package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources;

import fr.opensagres.eclipse.forms.editor.ModelEditorInput;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;

public class ResourceEditorInput  extends ModelEditorInput<Resource> {

	public ResourceEditorInput(Resource resource) {
		super(resource);
	}

	public String getName() {
		return getModel().getName();
	}

	public String getToolTipText() {
		return getName();
	}
}
