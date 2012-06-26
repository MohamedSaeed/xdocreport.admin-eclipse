package fr.opensagres.eclipse.forms.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public abstract class SelectionFormDialog extends SelectionDialog {

	private FormToolkit toolkit;

	public SelectionFormDialog(Shell parentShell) {
		super(parentShell);
	}

	public SelectionFormDialog() {
		super((Shell) null);
	}

	@Override
	public boolean close() {
		boolean rcode = super.close();
		toolkit.dispose();
		return rcode;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		ScrolledForm sform = toolkit.createScrolledForm(parent);
		sform.setLayoutData(new GridData(GridData.FILL_BOTH));
		ManagedForm mform = new ManagedForm(toolkit, sform);
		createFormContent(mform);
		applyDialogFont(sform.getBody());
		return sform;
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		// Composite sep = new Composite(parent, SWT.NULL);
		// sep.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		// gd.heightHint = 1;
		Label sep = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
		sep.setLayoutData(gd);
		Control bar = super.createButtonBar(parent);
		return bar;
	}

	/**
	 * Configures the dialog form and creates form content. Clients should
	 * override this method.
	 * 
	 * @param mform
	 *            the dialog form
	 */
	protected abstract void createFormContent(IManagedForm mform);
}
