package fr.opensagres.xdocreport.admin.eclipse.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import fr.opensagres.xdocreport.admin.domain.Repository;
import fr.opensagres.xdocreport.admin.eclipse.ui.dialogs.AddRepositoryDialog;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.FileResourceEditor;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.FolderResourceEditor;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.ResourceEditorInput;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.ImageResources;
import fr.opensagres.xdocreport.admin.services.RepositoryService;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;
import fr.opensagres.xdocreport.remoting.resources.domain.ResourceType;

public class View extends ViewPart implements IDoubleClickListener {
	public static final String ID = "fr.opensagres.xdocreport.admin.eclipse.ui.view";

	private RepositoryService repositoryService;

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	private TreeViewer viewer;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof Object[]) {
				return (Object[]) parent;
			}
			if (parent instanceof RepositoryService) {
				return ((RepositoryService) parent).getRepositories();
			}
			return new Object[0];
		}

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof Repository) {
				try {
					Resource resource = repositoryService.getResourcesService(
							(Repository) parentElement).getRoot();
					return resource.getChildren().toArray(new Resource[0]);
				} catch (Throwable e) {
					e.printStackTrace();
					// return ((IReportModule)
					// parentElement).getEntries().toArray();
				}
			}
			if (parentElement instanceof Resource) {
				Resource resource = (Resource) parentElement;
				return resource.getChildren().toArray(new Resource[0]);
			}
			return null;
		}

		public Object getParent(Object element) {
			return null;
		}

		public boolean hasChildren(Object element) {
			if (element instanceof Repository) {
				return true;
			}
			if (element instanceof Resource) {
				return ResourceType.FOLDER.equals(((Resource) element)
						.getType());
			}
			return false;
		}
	}

	class ViewLabelProvider extends LabelProvider {
		@Override
		public String getText(Object obj) {
			if (obj instanceof Repository) {
				return ((Repository) obj).getBaseAddress();
			}
			if (obj instanceof Resource) {
				return ((Resource) obj).getName();
			}
			return super.getText(obj);
		}

		@Override
		public Image getImage(Object obj) {
			if (obj instanceof Repository) {
				return ImageResources
						.getImage(ImageResources.IMG_REPOSITORY_16);
			}
			if (obj instanceof Resource) {
				Resource resource = (Resource) obj;
				if (ResourceType.FILE.equals(resource.getType())) {
					return ImageResources.getImage(ImageResources.IMG_FILE_16);
				}
				return ImageResources.getImage(ImageResources.IMG_FOLDER_16);
			}
			return PlatformUI.getWorkbench().getSharedImages()
					.getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(final Composite parent) {

		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		// Provide the input to the ContentProvider
		viewer.setInput(repositoryService);
		viewer.addDoubleClickListener(this);
		// viewer.expandAll();

		Action action = new Action() {
			@Override
			public void run() {
				AddRepositoryDialog dialog = new AddRepositoryDialog(
						parent.getShell());
				dialog.setRepositoryService(repositoryService);
				if (Window.OK == dialog.open()) {
					viewer.refresh();
				}
			}
		};
		action.setText("Add Repository");
		action.setImageDescriptor(ImageResources
				.getImageDescriptor(ImageResources.IMG_REPOSITORY_16));

		getViewSite().getActionBars().getToolBarManager().add(action);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void doubleClick(final DoubleClickEvent event) {
		ISelection selection = event.getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object element = structuredSelection.getFirstElement();
			if (element instanceof Resource) {
				try {
					Resource resource = (Resource) element;
					if (ResourceType.FILE.equals(resource.getType())) {
						getSite().getPage().openEditor(
								new ResourceEditorInput((Resource) element),
								FileResourceEditor.ID, true);
					} else {
						getSite().getPage().openEditor(
								new ResourceEditorInput((Resource) element),
								FolderResourceEditor.ID, true);
					}
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}