package fr.opensagres.xdocreport.admin.eclipse.ui.views;

import org.eclipse.jface.action.Action;

public class OpenEditorAction extends Action {

	private final RepositoryExplorer repositoryExplorer;
	private final Object element;

	public OpenEditorAction(RepositoryExplorer repositoryExplorer,
			Object element) {
		this.repositoryExplorer = repositoryExplorer;
		this.element = element;
		super.setText("Open");
	}

	@Override
	public void run() {
		repositoryExplorer.openEditor(element);
	}
}
