package fr.opensagres.xdocreport.admin.eclipse.ui.editors.repository;

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

import fr.opensagres.xdocreport.admin.domain.Repository;
import fr.opensagres.xdocreport.admin.eclipse.ui.FormLayoutFactory;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;

public class OverviewPage extends FormPage {

	private Text repositoryIdText;
	private Text repositoryNameText;

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
		section.setDescription(Messages.RepositoryEditor_OverviewPage_GeneralInfo_desc);
		section.setText(Messages.RepositoryEditor_OverviewPage_GeneralInfo_title);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		// glayout.horizontalSpacing = 10;
		glayout.numColumns = 2;
		sbody.setLayout(glayout);

		Repository repository = getRepository();

		// URL
		toolkit.createLabel(
				sbody,
				Messages.RepositoryEditor_OverviewPage_GeneralInfo_baseAddress_label);
		repositoryIdText = toolkit.createText(sbody,
				repository.getBaseAddress(), SWT.SINGLE);
		GridData repositoryIdGridData = new GridData(GridData.FILL_HORIZONTAL);
		repositoryIdGridData.widthHint = 150;
		repositoryIdText.setLayoutData(repositoryIdGridData);

		// SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);
	}

	@Override
	public RepositoryEditorInput getEditorInput() {
		return (RepositoryEditorInput) super.getEditorInput();
	}

	public Repository getRepository() {
		return getEditorInput().getRepository();
	}

}
