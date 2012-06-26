package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.template;

import fr.opensagres.eclipse.forms.ModelMasterDetailsBlock;
import fr.opensagres.eclipse.forms.editor.ModelToolbarMasterDetailsFormPage;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;

public class FieldsMetadataPage extends ModelToolbarMasterDetailsFormPage<Resource> {

	private static final String ID = "fieldsmetadata";

	public FieldsMetadataPage(TemplateResourceEditor editor) {
		super(editor, ID, Messages.TemplateResourceEditor_FieldsMetadataPage_title);
	}
	
	@Override
	protected ModelMasterDetailsBlock<Resource> createMasterDetailsBlock() {
		return new FieldsMetadataMasterDetailsBlock(this);
	}

}
