package fr.opensagres.eclipse.forms.validation;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class ValidationFormEditorSupport {

	private static final Object DEFAULT_MESSAGE_KEY = "k"; //$NON-NLS-1$

	public static AggregateValidationStatus createAggregateValidationStatus(
			final FormEditor editor, final DataBindingContext dataBindingContext) {
		AggregateValidationStatus aggregateStatus = new AggregateValidationStatus(
				dataBindingContext, AggregateValidationStatus.MAX_SEVERITY);
		aggregateStatus.addValueChangeListener(new IValueChangeListener() {

			public void handleValueChange(ValueChangeEvent event) {
				handleStateChange((IStatus) event.diff.getNewValue(), editor,
						dataBindingContext);

			}
		});
		return aggregateStatus;
	}

	private static void handleStateChange(IStatus newValue, FormEditor editor,
			DataBindingContext ctx) {
		IFormPage formPage = editor.getActivePageInstance();
		if (formPage == null) {
			return;
		}
		if (isDisposed(formPage)) {
			return;
		}
		for (Object o : ctx.getBindings()) {
			Binding binding = (Binding) o;
			IStatus status = (IStatus) binding.getValidationStatus().getValue();
			// if (newValue.equals(status)) {
			Control control = getControl(binding);
			if (control != null) {
				if (!status.isOK()) {
					formPage.getManagedForm()
							.getMessageManager()
							.addMessage(DEFAULT_MESSAGE_KEY,
									status.getMessage(), null,
									getMessageType(status), control);
					// return;
				} else {
					formPage.getManagedForm().getMessageManager()
							.removeMessage(DEFAULT_MESSAGE_KEY, control);
					// return;
				}
				// }
			}
		}

	}

	private static boolean isDisposed(IFormPage formPage) {
		IManagedForm managedForm = formPage.getManagedForm();
		if (managedForm == null) {
			return true;
		}
		ScrolledForm scrolledForm = managedForm.getForm();
		if (scrolledForm == null || scrolledForm.isDisposed()) {
			return true;
		}
		Form form = scrolledForm.getForm();
		if (form == null || form.isDisposed()) {
			return true;
		}
		Composite head = form.getHead();
		if (head == null || head.isDisposed()) {
			return true;
		}
		return false;
	}

	public static int getMessageType(IStatus status) {
		switch (status.getSeverity()) {
		case IStatus.ERROR:
			return IMessageProvider.ERROR;
		case IStatus.WARNING:
			return IMessageProvider.WARNING;
		case IStatus.INFO:
			return IMessageProvider.INFORMATION;
		}
		return IMessageProvider.NONE;
	}

	public static Control getControl(Binding binding) {
		if (binding.getTarget() instanceof ISWTObservable) {
			ISWTObservable swtObservable = (ISWTObservable) binding.getTarget();
			if (swtObservable.getWidget() instanceof Control) {
				return (Control) swtObservable.getWidget();
			}
		}
		return null;
	}
}