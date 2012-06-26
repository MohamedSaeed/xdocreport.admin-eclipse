package fr.opensagres.eclipse.forms.editor;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.ui.forms.IManagedForm;

import fr.opensagres.eclipse.forms.ModelMasterDetailsBlock;

public abstract class ModelMasterDetailsFormPage<Model> extends
		ModelFormPage<Model> {

	protected final ModelMasterDetailsBlock<Model> block;

	public ModelMasterDetailsFormPage(ModelFormEditor editor, String id,
			String title) {
		super(editor, id, title);
		block = createMasterDetailsBlock();
	}

	public ModelMasterDetailsFormPage(String id, String title) {
		super(id, title);
		block = createMasterDetailsBlock();
	}

	@Override
	protected void onCreateUI(IManagedForm managedForm) {
		block.createContent(managedForm);
	}

	public void onBind(DataBindingContext dataBindingContext) {
		block.onBind(dataBindingContext);
	}

	protected abstract ModelMasterDetailsBlock<Model> createMasterDetailsBlock();

}
