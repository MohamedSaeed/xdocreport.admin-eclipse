package fr.opensagres.eclipse.forms;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.AbstractFormPart;
import org.eclipse.ui.forms.IDetailsPage;

public abstract class AbstractDetailsPage extends AbstractFormPart implements
		IDetailsPage {

	public final void createContents(Composite parent) {
		onBeforeUI(parent);
		onCreateUI(parent);
		onAfterUI(parent);
	}

	protected void onBeforeUI(Composite parent) {

	}

	protected void onAfterUI(Composite parent) {

	}

	protected abstract void onCreateUI(Composite parent);

	
}
