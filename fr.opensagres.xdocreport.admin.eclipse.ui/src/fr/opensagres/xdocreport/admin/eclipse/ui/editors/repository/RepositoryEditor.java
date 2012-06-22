package fr.opensagres.xdocreport.admin.eclipse.ui.editors.repository;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import fr.opensagres.xdocreport.admin.eclipse.core.IRepositoryManager;

public class RepositoryEditor extends FormEditor {

	public static final String ID = "fr.opensagres.xdocreport.admin.eclipse.ui.editors.repository.RepositoryEditor";

	@Override
	protected void addPages() {
		try {
			super.addPage(new OverviewPage(this));
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
