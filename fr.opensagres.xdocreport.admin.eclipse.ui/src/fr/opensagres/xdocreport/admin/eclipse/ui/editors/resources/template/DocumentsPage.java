package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.template;

import fr.opensagres.eclipse.forms.ModelMasterDetailsBlock;
import fr.opensagres.eclipse.forms.editor.ModelToolbarMasterDetailsFormPage;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;

public class DocumentsPage extends ModelToolbarMasterDetailsFormPage<Resource> {

	private static final String ID = "documents";

	public DocumentsPage(TemplateResourceEditor editor) {
		super(editor, ID, Messages.TemplateResourceEditor_DocumentsPage_title);
	}
	
	@Override
	protected ModelMasterDetailsBlock<Resource> createMasterDetailsBlock() {
		return new DocumentsMasterDetailsBlock(this);
	}

}
