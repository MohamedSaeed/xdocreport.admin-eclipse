package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.document;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;

import fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.ResourceEditor;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.ResourceEditorInput;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;
import fr.opensagres.xdocreport.remoting.resources.domain.ResourceType;

public class DocumentResourceEditor extends
		ResourceEditor<ResourceEditorInput, Resource> {

	public static final String ID = "fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.document.DocumentResourceEditor";

	public DocumentResourceEditor() {
		super(ResourceType.DOCUMENT);
	}

	@Override
	protected Resource onSave(Resource modelObject, IProgressMonitor monitor) {
		// TODO: save it
		MessageDialog.openInformation(super.getSite().getShell(), "TODO",
				"Implements onSave to save Document with JAX-RS!");
		return modelObject;
	}

}
