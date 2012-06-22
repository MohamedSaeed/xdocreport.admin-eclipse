package fr.opensagres.xdocreport.admin.eclipse.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;

import fr.opensagres.xdocreport.admin.eclipse.ui.dialogs.AddRepositoryDialog;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.ImageResources;

public class AddRepositoryAction extends Action {

	private final RepositoryExplorer repositoryExplorer;

	public AddRepositoryAction(RepositoryExplorer repositoryExplorer) {
		this.repositoryExplorer = repositoryExplorer;
		super.setText("Add Repository");
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_REPOSITORY_NEW_16));
	}

	@Override
	public void run() {
		AddRepositoryDialog dialog = new AddRepositoryDialog(repositoryExplorer
				.getSite().getShell());
		dialog.setRepositoryService(repositoryExplorer.getRepositoryManager());
		if (Window.OK == dialog.open()) {
			repositoryExplorer.getViewer().refresh();
		}
	}

}
