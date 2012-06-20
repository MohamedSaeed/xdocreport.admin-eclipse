package fr.opensagres.xdocreport.admin.eclipse.ui.internal;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

import fr.opensagres.xdocreport.admin.eclipse.ui.Activator;

public class ImageResources {

	public final static String ICONS_PATH = "icons/"; //$NON-NLS-1$

	/**
	 * Set of predefined Image Descriptors.
	 */
	public static final String PATH_OBJ_16 = ICONS_PATH + "obj16/"; //$NON-NLS-1$

	private static final String MISSING = "icons/missing.png";

	public static final String IMG_REPOSITORY_16 = "repository_16";
	public static final String IMG_FOLDER_16 = "folder_16";
	public static final String IMG_FILE_16 = "file_16";

	public static void initialize(ImageRegistry imageRegistry) {
	//	registerImage(imageRegistry, Activator.PLUGIN_ID, MISSING);
		registerImage(imageRegistry, IMG_REPOSITORY_16, PATH_OBJ_16
				+ "repository.gif");
		registerImage(imageRegistry, IMG_FOLDER_16, PATH_OBJ_16 + "folder.gif");
		registerImage(imageRegistry, IMG_FILE_16, PATH_OBJ_16 + "file.gif");
	}
	
	private static void registerImage(ImageRegistry registry, String key,
			String fileName) {
		try {
			IPath path = new Path(fileName);
			Bundle bundle = Activator.getDefault().getBundle();
			URL url = FileLocator.find(bundle, path, null);
			if (url != null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				registry.put(key, desc);
			}
		} catch (Exception e) {
		}
	}

	private static void registerImageOLD(ImageRegistry registry,
			String symbolicName, String fileName) {
		try {
			// IPath path = new Path(fileName);
			// Bundle bundle = Activator.getDefault().getBundle();
			URL url = null;
			Bundle bundle = Platform.getBundle(symbolicName);
			if (bundle != null) {
				url = bundle.getEntry(fileName);
			}
			if (url != null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				registry.put(fileName, desc);
			} else {
				// not found... put "missing" image instead...
				registry.put(fileName, registry.get(MISSING));
			}
		} catch (Exception e) {
			registry.put(fileName, registry.get(MISSING));
		}
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
		ImageDescriptor img = imageRegistry.getDescriptor(key);
		if (img == null) {
			registerImage(imageRegistry, Activator.PLUGIN_ID, key);
			img = imageRegistry.getDescriptor(key);
		}
		return img;
	}

	// public static Image getImage(String pluginId, String key) {
	// ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
	// return imageRegistry.get(key);
	// }

	public static Image getImage(String key) {
		ImageRegistry imageRegistry = Activator.getDefault().getImageRegistry();
		Image img = imageRegistry.get(key);
		if (img == null) {
			registerImage(imageRegistry, Activator.PLUGIN_ID, key);
			img = imageRegistry.get(key);
		}
		return img;
	}

}
