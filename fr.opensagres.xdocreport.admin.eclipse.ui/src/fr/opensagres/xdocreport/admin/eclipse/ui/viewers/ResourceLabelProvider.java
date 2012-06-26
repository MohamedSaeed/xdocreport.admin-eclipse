package fr.opensagres.xdocreport.admin.eclipse.ui.viewers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import fr.opensagres.xdocreport.admin.eclipse.ui.internal.ImageResources;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;

public class ResourceLabelProvider extends LabelProvider {

	private static ResourceLabelProvider instance;

	public static ResourceLabelProvider getInstance() {
		synchronized (ResourceLabelProvider.class) {
			if (instance == null) {
				instance = new ResourceLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Resource) {
			return ((Resource) element).getName();
		}
		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof Resource) {
			Resource resource = (Resource) element;
			switch (resource.getType()) {
			case DOCUMENT:
				return ImageResources.getImage(ImageResources.IMG_DOCUMENT_16);
			case TEMPLATE:
				return ImageResources.getImage(ImageResources.IMG_TEMPLATE_16);
			default:
				return ImageResources.getImage(ImageResources.IMG_CATEGORY_16);
			}
		}
		return super.getImage(element);
	}
}
