package fr.opensagres.xdocreport.admin.eclipse.ui.dialogs;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import fr.opensagres.xdocreport.admin.domain.Repository;
import fr.opensagres.xdocreport.admin.services.RepositoryService;

public class AddRepositoryDialog extends TrayDialog {

	private RepositoryService repositoryService;

	private Text baseAddressText;

	private Text userNameText;

	private Text passwordText;

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public AddRepositoryDialog(Shell shell) {
		super(shell);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
	    final GridLayout gridLayout = new GridLayout();
	    gridLayout.numColumns = 2;
	    container.setLayout(gridLayout);
		
	    // Base address
	    Label baseAddressLabel = new Label(container, SWT.NONE);
	    baseAddressLabel.setText("URL:");
		baseAddressText = new Text(container, SWT.BORDER);
		baseAddressText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
	    // User name
	    Label userNameLabel = new Label(container, SWT.NONE);
	    userNameLabel.setText("User:");
		userNameText = new Text(container, SWT.BORDER);
		userNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// User name
	    Label passwordLabel = new Label(container, SWT.NONE);
	    passwordLabel.setText("Password:");
		passwordText = new Text(container, SWT.BORDER);
		passwordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		return container;
	}

	@Override
	protected void okPressed() {

		Repository repository = new Repository();
		repository.setBaseAddress(baseAddressText.getText());
		repository.setUsername(userNameText.getText());
		repository.setPassword(passwordText.getText());		

		repositoryService.saveRepository(repository);
		super.okPressed();
	}

}
