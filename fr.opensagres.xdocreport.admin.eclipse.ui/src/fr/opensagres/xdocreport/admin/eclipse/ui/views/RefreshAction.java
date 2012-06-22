package fr.opensagres.xdocreport.admin.eclipse.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;

import fr.opensagres.xdocreport.admin.eclipse.ui.internal.ImageResources;

public class RefreshAction extends Action {

	private final TreeViewer viewer;
	private final Object element;
	
	public RefreshAction(TreeViewer viewer, Object element) {
		super.setText("Refresh");
		super.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_REPOSITORY_REFRESH_16));
		this.viewer = viewer;
		this.element=element;
	}

	@Override
	public void run() {
		viewer.expandToLevel(element,  1);
		viewer.refresh(element);
	}
}
