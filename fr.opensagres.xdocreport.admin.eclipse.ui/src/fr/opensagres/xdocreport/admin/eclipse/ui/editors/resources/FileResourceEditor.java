package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources;

import fr.opensagres.xdocreport.remoting.resources.domain.ResourceType;

public class FileResourceEditor extends ResourceEditor {

	public static final String ID = "fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.FileResourceEditor";

	public FileResourceEditor() {
		super(ResourceType.DOCUMENT);
	}
}
