package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.template;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import fr.opensagres.eclipse.forms.editor.ModelToolbarFormPage;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;

public class FieldsMetadataPage extends ModelToolbarFormPage<Resource> {

	private static final String ID = "fieldsmetadata";

	public FieldsMetadataPage(TemplateResourceEditor editor) {
		super(editor, ID, Messages.TemplateResourceEditor_FieldsMetadataPage_title);
	}

	public void onBind(DataBindingContext dataBindingContext) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		// TODO Auto-generated method stub

	}

}
