package fr.opensagres.eclipse.forms;

import org.eclipse.core.databinding.DataBindingContext;

import fr.opensagres.eclipse.forms.editor.ModelFormEditor;
import fr.opensagres.eclipse.forms.editor.ModelMasterDetailsFormPage;

public abstract class ModelMasterDetailsBlock<Model> extends
		AbstractMasterDetailsBlock {

	private final ModelMasterDetailsFormPage<Model> detailsPage;

	public ModelMasterDetailsBlock(ModelMasterDetailsFormPage<Model> detailsPage) {
		this.detailsPage = detailsPage;
	}

	public ModelMasterDetailsFormPage<Model> getDetailsPage() {
		return detailsPage;
	}

	public Model getModelObject() {
		return (Model) getDetailsPage().getModelObject();
	}

	public ModelFormEditor getEditor() {
		return getDetailsPage().getEditor();
	}
	
	public abstract void onBind(DataBindingContext dataBindingContext);

}
