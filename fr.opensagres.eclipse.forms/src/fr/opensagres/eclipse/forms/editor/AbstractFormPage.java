package fr.opensagres.eclipse.forms.editor;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

public abstract class AbstractFormPage extends FormPage {

	public AbstractFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	public AbstractFormPage(String id, String title) {
		super(id, title);
	}

	@Override
	protected final void createFormContent(IManagedForm managedForm) {
		onBeforeUI(managedForm);
		onCreateUI(managedForm);
		onAfterUI(managedForm);
	}

	protected void onBeforeUI(IManagedForm managedForm) {
		// Do nothing
	}

	protected void onAfterUI(IManagedForm managedForm) {
		// Do nothing
	}

	protected Display getDisplay() {
		return getSite().getShell().getDisplay();
	}
	
	/**
	 * Implement onCreateUI to create your UI.
	 * 
	 * @param managedForm
	 */
	protected abstract void onCreateUI(IManagedForm managedForm);

}
