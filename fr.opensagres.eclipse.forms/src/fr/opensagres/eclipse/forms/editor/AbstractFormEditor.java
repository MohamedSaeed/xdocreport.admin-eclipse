package fr.opensagres.eclipse.forms.editor;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import fr.opensagres.eclipse.forms.registry.EditorPageDescriptor;
import fr.opensagres.eclipse.forms.registry.EditorPagesRegistry;

public abstract class AbstractFormEditor<EditorInput extends IEditorInput>
		extends FormEditor {

	@Override
	protected final void addPages() {
		onBeforeAddPages();
		doAddPages();
		try {
			doAddExtensionPages();
		} catch (Exception e) {
			e.printStackTrace();
		}
		onAfterAddPages();
	}

	protected void onAfterAddPages() {
		// Do nothing
	}

	protected void onBeforeAddPages() {
		// Do nothing
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	public void doSave(IProgressMonitor monitor) {
		onBeforeSave(monitor);
		onSave(monitor);
		onAfterSave(monitor);
	}

	protected void onBeforeSave(IProgressMonitor monitor) {
		commitPages(true);
	}

	protected void onAfterSave(IProgressMonitor monitor) {
		editorDirtyStateChanged();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public EditorInput getEditorInput() {
		return (EditorInput) super.getEditorInput();
	}

	private void doAddExtensionPages() throws CoreException, PartInitException {
		String editorId = getEditorId();
		if (editorId != null) {

			List<EditorPageDescriptor> editorDescriptor = EditorPagesRegistry
					.getRegistry().getDescriptors(editorId);
			if (editorDescriptor != null) {
				int pageIndex = 0;
				String pageText = null;
				for (EditorPageDescriptor descriptor : editorDescriptor) {
					IEditorPart page = descriptor.createPage(this);
					pageIndex = this.addPage(page, super.getEditorInput());
					pageText = descriptor.getTitle();
					if (pageText != null) {
						this.setPageText(pageIndex, pageText);
					}

				}
			}
		}
	}

	protected String getEditorId() {
		return null;
	}

	protected abstract void doAddPages();

	protected abstract void onSave(IProgressMonitor monitor);
}
