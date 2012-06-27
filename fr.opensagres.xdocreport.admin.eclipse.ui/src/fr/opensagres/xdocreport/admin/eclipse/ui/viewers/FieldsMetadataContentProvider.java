package fr.opensagres.xdocreport.admin.eclipse.ui.viewers;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class FieldsMetadataContentProvider implements ITreeContentProvider {

	private static FieldsMetadataContentProvider instance;

	public static FieldsMetadataContentProvider getInstance() {
		synchronized (FieldsMetadataContentProvider.class) {
			if (instance == null) {
				instance = new FieldsMetadataContentProvider();
			}
			return instance;
		}
	}

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof FieldMetadataTreeNodeModel) {
			return ((FieldMetadataTreeNodeModel) inputElement).getNodes()
					.toArray(new FieldMetadataTreeNodeModel[0]);
		}
		return new Object[0];
	}

	public Object[] getChildren(Object parentElement) {
		return getElements(parentElement);
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

}
