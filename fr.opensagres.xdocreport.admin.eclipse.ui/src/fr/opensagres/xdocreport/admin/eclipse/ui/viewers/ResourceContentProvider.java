package fr.opensagres.xdocreport.admin.eclipse.ui.viewers;

import org.eclipse.jface.viewers.ArrayContentProvider;

public class ResourceContentProvider extends ArrayContentProvider {

	private static ResourceContentProvider instance;

	public static ResourceContentProvider getInstance() {
		synchronized (ResourceContentProvider.class) {
			if (instance == null) {
				instance = new ResourceContentProvider();
			}
			return instance;
		}
	}

}
