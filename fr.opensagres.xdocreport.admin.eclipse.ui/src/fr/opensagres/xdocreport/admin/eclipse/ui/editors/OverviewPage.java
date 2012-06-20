package fr.opensagres.xdocreport.admin.eclipse.ui.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.xdocreport.admin.eclipse.ui.FormLayoutFactory;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;

public class OverviewPage extends FormPage {

	private Text resourceIdText;
	private Text resourceNameText;

	public OverviewPage(FormEditor editor) {
		super(editor, "overview", "Overview");
	}

	@Override
	protected final void createFormContent(IManagedForm managedForm) {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(FormLayoutFactory.createFormTableWrapLayout(true, 2));

		FormToolkit toolkit = managedForm.getToolkit();
		Composite left = toolkit.createComposite(body);
		left.setLayout(FormLayoutFactory
				.createFormPaneTableWrapLayout(false, 1));
		left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		// General info section
		createGeneralInfoSection(toolkit, left);

		// Address section
		// createAddressSection(toolkit, left);

		Composite right = toolkit.createComposite(body);
		right.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false,
				1));
		right.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

		// Content section
		// createContentSection(toolkit, right);

		// createResumeInfoSection(toolkit, right);
	}

	private void createGeneralInfoSection(FormToolkit toolkit, Composite left) {
		Section section = toolkit.createSection(left, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setDescription("");
		section.setText("General Information");
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		// glayout.horizontalSpacing = 10;
		glayout.numColumns = 2;
		sbody.setLayout(glayout);

		Resource resource = getResource();

		// ID
		toolkit.createLabel(sbody, "ID:");
		resourceIdText = toolkit
				.createText(sbody, resource.getId(), SWT.SINGLE);
		GridData resourceIdGridData = new GridData(GridData.FILL_HORIZONTAL);
		resourceIdGridData.widthHint = 150;
		resourceIdText.setLayoutData(resourceIdGridData);

		// Name
		toolkit.createLabel(sbody, "Name:");
		resourceNameText = toolkit
				.createText(sbody, resource.getName(), SWT.SINGLE);
		GridData resourceNameGridData = new GridData(GridData.FILL_HORIZONTAL);
		resourceNameGridData.widthHint = 150;
		resourceNameText.setLayoutData(resourceNameGridData);

		// SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);
	}

	@Override
	public ResourceEditorInput getEditorInput() {
		// TODO Auto-generated method stub
		return (ResourceEditorInput) super.getEditorInput();
	}

	public Resource getResource() {
		return getEditorInput().getResource();
	}

}
