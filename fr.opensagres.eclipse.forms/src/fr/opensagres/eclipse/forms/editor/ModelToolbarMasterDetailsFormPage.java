package fr.opensagres.eclipse.forms.editor;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import fr.opensagres.eclipse.forms.editor.ModelMasterDetailsFormPage;

public abstract class ModelToolbarMasterDetailsFormPage<Model> extends
		ModelMasterDetailsFormPage<Model> {

	public ModelToolbarMasterDetailsFormPage(ModelFormEditor editor,
			String id, String title) {
		super(editor, id, title);
	}

	@Override
	protected void onCreateUI(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		toolkit.decorateFormHeading(form.getForm());

		IToolBarManager manager = form.getToolBarManager();
		getEditor().contributeToToolbar(manager);
		form.updateToolBar();
		form.setText(getFormTitleText());
		Image titleImage = getFormTitleImage();
		if (titleImage != null) {
			form.setImage(titleImage);
		}
		toolkit.decorateFormHeading(form.getForm());
		fillBody(managedForm, toolkit);
	}

	protected String getFormTitleText() {
		return getTitle();
	}

	protected Image getFormTitleImage() {
		return null;
	}

	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		super.onCreateUI(managedForm);
	}

}
