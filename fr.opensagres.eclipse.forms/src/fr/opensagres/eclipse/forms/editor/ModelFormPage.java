package fr.opensagres.eclipse.forms.editor;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.ui.forms.IManagedForm;

import fr.opensagres.eclipse.forms.IBindableAware;

public abstract class ModelFormPage<Model> extends AbstractFormPage implements
		IBindableAware {

	public ModelFormPage(ModelFormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	public ModelFormPage(String id, String title) {
		super(id, title);
	}

	@Override
	protected void onAfterUI(IManagedForm managedForm) {
		super.onAfterUI(managedForm);
		bind();
	}

	public void bind() {
		DataBindingContext dataBindingContext = getEditor()
				.getDatabindingContext(getDatabindingContentId());
		getEditor().bind(this, dataBindingContext);
	}

	public Model getModelObject() {
		return (Model) getEditor().getModelObject();
	}

	protected String getDatabindingContentId() {
		return ModelFormEditor.SHARED_DATABINDING_CONTEXT_ID;
	}

	@Override
	public ModelFormEditor getEditor() {
		return (ModelFormEditor) super.getEditor();
	}

}
