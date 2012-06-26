package fr.opensagres.xdocreport.admin.eclipse.ui.editors.repository;

import fr.opensagres.eclipse.forms.editor.ModelEditorInput;
import fr.opensagres.xdocreport.admin.eclipse.core.Repository;

public class RepositoryEditorInput extends ModelEditorInput<Repository> {

	public RepositoryEditorInput(Repository repository) {
		super(repository);
	}

	public String getName() {
		return getModel().getBaseAddress();
	}

	public String getToolTipText() {
		return getName();
	}

}
