package fr.opensagres.xdocreport.admin.eclipse.ui.dialogs;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.opensagres.xdocreport.admin.eclipse.core.Repository;
import fr.opensagres.xdocreport.admin.eclipse.core.IRepositoryManager;
import fr.opensagres.xdocreport.admin.eclipse.ui.Activator;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;
import fr.opensagres.xdocreport.commons.utils.StringUtils;
import fr.opensagres.xdocreport.remoting.resources.services.ResourcesService;
import fr.opensagres.xdocreport.remoting.resources.services.ServiceType;

public class AddRepositoryDialog extends TitleAreaDialog {

	private IRepositoryManager repositoryService;

	private Text baseAddressText;

	private Text userNameText;

	private Text passwordText;

	private Button allowChunkingCheckbox;

	private Text connectionTimeoutText;

	private ComboViewer serviceTypeViewer;

	public void setRepositoryService(IRepositoryManager repositoryService) {
		this.repositoryService = repositoryService;
	}

	public AddRepositoryDialog(Shell shell) {
		super(shell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite parentComposite = (Composite) super.createDialogArea(parent);

		Composite container = new Composite(parentComposite, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		super.setTitle(Messages.AddRepositoryDialog_title);

		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);

		// Service Type
		Label serviceTypeLabel = new Label(container, SWT.NONE);
		serviceTypeLabel
				.setText(Messages.AddRepositoryDialog_serviceType_label);
		serviceTypeViewer = new ComboViewer(container, SWT.READ_ONLY
				| SWT.DROP_DOWN);
		serviceTypeViewer
				.setContentProvider(ArrayContentProvider.getInstance());
		serviceTypeViewer.setLabelProvider(new LabelProvider());
		serviceTypeViewer.setInput(ServiceType.values());
		serviceTypeViewer.getControl().setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));
		serviceTypeViewer.getCombo().addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						validate();
					}
				});

		// Base address
		Label baseAddressLabel = new Label(container, SWT.NONE);
		baseAddressLabel
				.setText(Messages.AddRepositoryDialog_baseAddress_label);
		baseAddressText = new Text(container, SWT.BORDER);
		baseAddressText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		baseAddressText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// User name
		Label userNameLabel = new Label(container, SWT.NONE);
		userNameLabel.setText(Messages.AddRepositoryDialog_user_label);
		userNameText = new Text(container, SWT.BORDER);
		userNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Password
		Label passwordLabel = new Label(container, SWT.NONE);
		passwordLabel.setText(Messages.AddRepositoryDialog_password_label);
		passwordText = new Text(container, SWT.BORDER);
		passwordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Allow Chunking?
		Label allowChunkingLabel = new Label(container, SWT.NONE);
		allowChunkingLabel
				.setText(Messages.AddRepositoryDialog_allowChunking_label);
		allowChunkingCheckbox = new Button(container, SWT.CHECK);
		allowChunkingCheckbox.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		// Connection Timeout
		Label connectionTimeoutLabel = new Label(container, SWT.NONE);
		connectionTimeoutLabel
				.setText(Messages.AddRepositoryDialog_connectionTimeout_label);
		connectionTimeoutText = new Text(container, SWT.BORDER);
		connectionTimeoutText.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));

		Dialog.applyDialogFont(parentComposite);

		Point defaultMargins = LayoutConstants.getMargins();
		GridLayoutFactory.fillDefaults().numColumns(2)
				.margins(defaultMargins.x, defaultMargins.y)
				.generateLayout(container);
		return container;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		validate();
		return contents;
	}

	@Override
	protected void okPressed() {
		Repository repository = new Repository();
		repository
				.setServiceType((ServiceType) ((IStructuredSelection) serviceTypeViewer
						.getSelection()).getFirstElement());
		repository.setBaseAddress(baseAddressText.getText());
		repository.setUsername(userNameText.getText());
		repository.setPassword(passwordText.getText());
		repository.setAllowChunking(allowChunkingCheckbox.getSelection());
		Long timeout = null;
		try {
			timeout = Long.parseLong(connectionTimeoutText.getText());
		} catch (Throwable e) {

		}
		repository.setConnectionTimeout(timeout);

		// Check id repository is OK
		if (isRepositoryOK(repository)) {
			repositoryService.saveRepository(repository);
			super.okPressed();
		}
	}

	private boolean isRepositoryOK(Repository repository) {
		try {
			ResourcesService resourcesService = repositoryService
					.getResourcesService(repository);
			resourcesService.getName();
			return true;
		} catch (Throwable e) {

			errorDialogWithStackTrace(super.getShell(), "Bad repository",
					"Repository connection error", e);
			return MessageDialog.openConfirm(super.getShell(),
					"Bad repository",
					"The repository is bad, do you wish save it?");
		}
	}

	private void validate() {
		super.getButton(IDialogConstants.OK_ID).setEnabled(validateFields());
	}

	private boolean validateFields() {
		// Validate service type
		if (serviceTypeViewer.getSelection().isEmpty()) {
			setErrorMessage(Messages.AddRepositoryDialog_serviceType_validation_required);
			return false;
		}
		// Validate URL
		if (StringUtils.isEmpty(baseAddressText.getText())) {
			setErrorMessage(Messages.AddRepositoryDialog_baseAddress_validation_required);
			return false;
		}
		setErrorMessage(null);
		return true;
	}

	/**
	 * Shows JFace ErrorDialog but improved by constructing full stack trace in
	 * detail area.
	 */
	public static void errorDialogWithStackTrace(Shell parent,
			String dialogTitle, String message, Throwable t) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);

		final String trace = sw.toString(); // stack trace as a string

		// Temp holder of child statuses
		List<Status> childStatuses = new ArrayList<Status>();

		// Split output by OS-independend new-line
		for (String line : trace.split(System.getProperty("line.separator"))) {
			// build & add status
			childStatuses.add(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					line));
		}

		MultiStatus ms = new MultiStatus(Activator.PLUGIN_ID, IStatus.ERROR,
				childStatuses.toArray(new Status[] {}), // convert to array of
														// statuses
				t.getLocalizedMessage(), t);

		ErrorDialog.openError(parent, dialogTitle, message, ms);
	}
}
