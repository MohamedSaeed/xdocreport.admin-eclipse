package fr.opensagres.xdocreport.admin.eclipse.ui.editors.repository;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.rap.singlesourcing.SingleSourcingUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.xdocreport.admin.eclipse.core.Repository;
import fr.opensagres.xdocreport.admin.eclipse.ui.FormLayoutFactory;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;
import fr.opensagres.xdocreport.remoting.resources.services.ServiceType;

public class OverviewPage extends FormPage {

	private Text baseAddressText;
	private Text repositoryNameText;
	private Text userNameText;
	private Text passwordText;
	private Text connectionTimeoutText;
	private Button allowChunkingCheckbox;
	private ComboViewer serviceTypeViewer;

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

		// Service Type
		toolkit.createLabel(
				sbody,
				Messages.RepositoryEditor_OverviewPage_GeneralInfo_serviceType_label);
		serviceTypeViewer = new ComboViewer(sbody, SWT.READ_ONLY
				| SWT.DROP_DOWN);
		serviceTypeViewer
				.setContentProvider(ArrayContentProvider.getInstance());
		serviceTypeViewer.setLabelProvider(new LabelProvider());
		serviceTypeViewer.setInput(ServiceType.values());
		serviceTypeViewer.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));
		serviceTypeViewer.setSelection(new StructuredSelection(repository
				.getServiceType()));

		// URL
		toolkit.createLabel(
				sbody,
				Messages.RepositoryEditor_OverviewPage_GeneralInfo_baseAddress_label);
		baseAddressText = toolkit.createText(sbody,
				repository.getBaseAddress(), SWT.SINGLE);
		GridData baseAddressGridData = new GridData(GridData.FILL_HORIZONTAL);
		baseAddressGridData.widthHint = 150;
		baseAddressText.setLayoutData(baseAddressGridData);

		// Username
		toolkit.createLabel(
				sbody,
				Messages.RepositoryEditor_OverviewPage_GeneralInfo_userName_label);
		userNameText = toolkit.createText(sbody, repository.getUsername(),
				SWT.SINGLE);
		GridData userNameGridData = new GridData(GridData.FILL_HORIZONTAL);
		userNameGridData.widthHint = 150;
		userNameText.setLayoutData(userNameGridData);

		// Password
		toolkit.createLabel(
				sbody,
				Messages.RepositoryEditor_OverviewPage_GeneralInfo_password_label);
		passwordText = toolkit.createText(sbody, repository.getPassword(),
				SWT.SINGLE);
		GridData passwordGridData = new GridData(GridData.FILL_HORIZONTAL);
		passwordGridData.widthHint = 150;
		passwordText.setLayoutData(passwordGridData);

		// Password
		toolkit.createLabel(
				sbody,
				Messages.RepositoryEditor_OverviewPage_GeneralInfo_allowChunking_label);
		allowChunkingCheckbox = toolkit.createButton(sbody, "", SWT.CHECK);
		GridData allowChunkingGridData = new GridData(GridData.FILL_HORIZONTAL);
		allowChunkingGridData.widthHint = 150;
		allowChunkingCheckbox.setLayoutData(allowChunkingGridData);
		if (repository.getAllowChunking() != null) {
			allowChunkingCheckbox.setSelection(repository.getAllowChunking());
		}

		// Connection Timeout
		toolkit.createLabel(
				sbody,
				Messages.RepositoryEditor_OverviewPage_GeneralInfo_connectionTimeout_label);
		connectionTimeoutText = toolkit.createText(
				sbody,
				repository.getConnectionTimeout() != null ? String
						.valueOf(repository.getConnectionTimeout()) : "",
				SWT.SINGLE);
		GridData connectionTimeoutGridData = new GridData(
				GridData.FILL_HORIZONTAL);
		connectionTimeoutGridData.widthHint = 150;
		connectionTimeoutText.setLayoutData(connectionTimeoutGridData);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);
	}

	@Override
	public RepositoryEditorInput getEditorInput() {
		return (RepositoryEditorInput) super.getEditorInput();
	}

	public Repository getRepository() {
		return getEditorInput().getRepository();
	}

}
