package fr.opensagres.xdocreport.admin.eclipse.ui.editors.repository;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

import fr.opensagres.eclipse.forms.editor.ModelFormEditor;
import fr.opensagres.xdocreport.admin.eclipse.core.DomainHelper;
import fr.opensagres.xdocreport.admin.eclipse.core.Platform;
import fr.opensagres.xdocreport.admin.eclipse.core.Repository;

public class RepositoryEditor extends
		ModelFormEditor<RepositoryEditorInput, Repository> {

	public static final String ID = "fr.opensagres.xdocreport.admin.eclipse.ui.editors.repository.RepositoryEditor";

	@Override
	protected void doAddPages() {
		try {
			super.addPage(new OverviewPage(this));
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Repository onLoad(RepositoryEditorInput input) {
		return DomainHelper.clone(input.getModel());
	}


	@Override
	protected Repository onSave(Repository repository, IProgressMonitor monitor) {
		return Platform.getRepositoryManager().saveRepository(repository);
	}

}
