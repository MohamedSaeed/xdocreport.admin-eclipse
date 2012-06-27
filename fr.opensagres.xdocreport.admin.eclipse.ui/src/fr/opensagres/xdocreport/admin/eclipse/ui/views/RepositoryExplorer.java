package fr.opensagres.xdocreport.admin.eclipse.ui.views;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import fr.opensagres.xdocreport.admin.eclipse.core.IRepositoryManager;
import fr.opensagres.xdocreport.admin.eclipse.core.Platform;
import fr.opensagres.xdocreport.admin.eclipse.core.Repository;
import fr.opensagres.xdocreport.admin.eclipse.ui.Activator;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.repository.RepositoryEditor;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.repository.RepositoryEditorInput;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.ResourceEditorInput;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.category.CategoryResourceEditor;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.document.DocumentResourceEditor;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.template.TemplateResourceEditor;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.ImageResources;
import fr.opensagres.xdocreport.admin.eclipse.ui.viewers.ResourceLabelProvider;
import fr.opensagres.xdocreport.commons.utils.StringUtils;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;
import fr.opensagres.xdocreport.remoting.resources.domain.ResourceHelper;
import fr.opensagres.xdocreport.remoting.resources.domain.ResourceType;

public class RepositoryExplorer extends ViewPart implements
		IDoubleClickListener {

	public static final String ID = "fr.opensagres.xdocreport.admin.eclipse.ui.views.RepositoryExplorer";

	private IRepositoryManager repositoryManager;

	private TreeViewer viewer;

	public RepositoryExplorer() {
		this.repositoryManager = Platform.getRepositoryManager();
	}

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
			if (parent instanceof IRepositoryManager) {
				return ((IRepositoryManager) parent).getRepositories();
			}
			return new Object[0];
		}

		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof Repository) {
				try {
					Resource resource = repositoryManager.getResourcesService(
							(Repository) parentElement).getRoot();
					return resource.getChildren().toArray(new Resource[0]);
				} catch (Throwable e) {
					Status status = new Status(IStatus.ERROR,
							Activator.PLUGIN_ID, 0, "Repository connection", e);
					ErrorDialog.openError(viewer.getControl().getShell(),
							"RepositoryService Error", e.getMessage(), status);
					return null;
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
				return !ResourceType.DOCUMENT.equals(((Resource) element)
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
				return (ResourceLabelProvider.getInstance().getText(obj));
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
				return ResourceLabelProvider.getInstance().getImage(obj);
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
		viewer.setInput(repositoryManager);
		viewer.addDoubleClickListener(this);
		initPopupMenu();
		// viewer.expandAll();

		Action action = new AddRepositoryAction(this);
		getViewSite().getActionBars().getToolBarManager().add(action);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void doubleClick(final DoubleClickEvent event) {
		Object element = getFirstSelectedElement(event.getSelection());
		Repository repository = getSelectedRepository(event.getSelection());
		openEditor(repository, element);
	}

	private Repository getSelectedRepository(ISelection selection) {
		return (Repository) ((ITreeSelection) selection).getPaths()[0]
				.getFirstSegment();
	}

	public void openEditor(Repository selectedRepository, Object element) {
		try {
			if (element instanceof Resource) {
				Resource resource = (Resource) element;
				switch (resource.getType()) {
				case DOCUMENT:
					getSite().getPage().openEditor(
							new ResourceEditorInput(selectedRepository, resource),
							DocumentResourceEditor.ID, true);
					break;
				case CATEGORY:
					getSite().getPage().openEditor(
							new ResourceEditorInput(selectedRepository, resource),
							CategoryResourceEditor.ID, false);
					break;
				case TEMPLATE:
					getSite().getPage().openEditor(
							new ResourceEditorInput(selectedRepository, resource),
							TemplateResourceEditor.ID, true);
					break;
				}
			} else if (element instanceof Repository) {
				Repository repository = (Repository) element;
				getSite().getPage().openEditor(
						new RepositoryEditorInput(repository),
						RepositoryEditor.ID, true);
			}
		} catch (PartInitException e) {
			Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
					"Repository connection", e);
			ErrorDialog.openError(viewer.getControl().getShell(),
					"UserService Error", e.getMessage(), status);

		}
	}

	public Object getFirstSelectedElement() {
		return getFirstSelectedElement(viewer.getSelection());
	}

	private Object getFirstSelectedElement(ISelection selection) {
		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			return structuredSelection.getFirstElement();
		}
		return null;
	}

	private void initPopupMenu() {
		MenuManager menuManager = initMenuManager();
		Menu menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuManager, viewer);
	}

	private MenuManager initMenuManager() {
		// initalize the context menu
		final MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				Object element = getFirstSelectedElement(viewer.getSelection());

				// Open editor
				Repository repository = getSelectedRepository(viewer.getSelection());
				OpenEditorAction openEditorAction = new OpenEditorAction(
						RepositoryExplorer.this, repository, element);
				manager.add(openEditorAction);

				if (element instanceof Repository) {

					RefreshAction refreshAction = new RefreshAction(viewer,
							element);
					manager.add(refreshAction);

					DeleteAction deleteAction = new DeleteAction(
							RepositoryExplorer.this);
					manager.add(deleteAction);

				} else if (element instanceof Resource) {

				}

			}

		});
		return menuMgr;
	}

	public TreeViewer getViewer() {
		return viewer;
	}

	public IRepositoryManager getRepositoryManager() {
		return repositoryManager;
	}
}