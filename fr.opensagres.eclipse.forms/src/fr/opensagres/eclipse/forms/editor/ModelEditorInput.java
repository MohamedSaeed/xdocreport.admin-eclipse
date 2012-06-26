package fr.opensagres.eclipse.forms.editor;

public abstract class ModelEditorInput<Model> extends AbstractEditorInput
		implements IModelProvider<Model> {

	private final Model model;

	public ModelEditorInput(Model model) {
		this.model = model;
	}

	public Model getModel() {
		return model;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IModelProvider) {
			return this.getModel().equals(((IModelProvider) obj).getModel());
		}
		return super.equals(obj);
	}
}
