package fr.opensagres.xdocreport.admin.eclipse.ui.viewers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class FieldsMetadataLabelProvider extends LabelProvider {

	private static FieldsMetadataLabelProvider instance;

	public static FieldsMetadataLabelProvider getInstance() {
		synchronized (FieldsMetadataLabelProvider.class) {
			if (instance == null) {
				instance = new FieldsMetadataLabelProvider();
			}
			return instance;
		}
	}

	@Override
	public String getText(Object element) {
		if (element instanceof FieldMetadataTreeNodeModel) {
			return ((FieldMetadataTreeNodeModel)element).getName();
		}
		return super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		return super.getImage(element);
	}
}
