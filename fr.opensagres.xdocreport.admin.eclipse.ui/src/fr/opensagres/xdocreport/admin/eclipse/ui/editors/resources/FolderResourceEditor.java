package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources;

import fr.opensagres.xdocreport.remoting.resources.domain.ResourceType;

public class FolderResourceEditor extends ResourceEditor {

	public static final String ID = "fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.FolderResourceEditor";
	
	public FolderResourceEditor() {
		super(ResourceType.CATEGORY);
	}
}
