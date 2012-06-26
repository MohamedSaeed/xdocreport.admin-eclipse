package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.template;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.rap.singlesourcing.SingleSourcingUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import fr.opensagres.eclipse.forms.editor.ModelDetailsPage;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;

public class FieldMetadataDetailsPage extends ModelDetailsPage<Resource> {

	private Text hobbyLabelText;

	@Override
	protected void onCreateUI(Composite parent) {

		TableWrapLayout layout = new TableWrapLayout();
		layout.topMargin = 5;
		layout.leftMargin = 5;
		layout.rightMargin = 2;
		layout.bottomMargin = 2;
		parent.setLayout(layout);

		FormToolkit toolkit = getManagedForm().getToolkit();

		Section hobbyDetailSection = toolkit.createSection(parent,
				Section.DESCRIPTION | Section.TITLE_BAR);
		hobbyDetailSection.marginWidth = 10;
		hobbyDetailSection
				.setText(Messages.TemplateResourceEditor_DocumentsPage_DocumentDetailsPage_title); //$NON-NLS-1$
		hobbyDetailSection
				.setDescription(Messages.TemplateResourceEditor_DocumentsPage_DocumentDetailsPage_desc); //$NON-NLS-1$

		TableWrapData td = new TableWrapData(TableWrapData.FILL,
				TableWrapData.TOP);
		td.grabHorizontal = true;
		hobbyDetailSection.setLayoutData(td);

		Composite client = toolkit.createComposite(hobbyDetailSection);
		hobbyDetailSection.setClient(client);

		// Create generic content
		createBodyContent(toolkit, client);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, client);
	}

	private void createBodyContent(FormToolkit toolkit, Composite parent) {
		GridLayout glayout = new GridLayout();
		glayout.numColumns = 2;
		parent.setLayout(glayout);

		// Hobby label
		toolkit.createLabel(
				parent,
				Messages.TemplateResourceEditor_DocumentsPage_DocumentDetailsPage_templateName_label);
		hobbyLabelText = toolkit.createText(parent, "", SWT.SINGLE);
		hobbyLabelText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));


	}

	public void onBind(DataBindingContext bindingContext) {

		// Label binding
		IObservableValue hobbyLabelTextObserveTextObserveWidget = SWTObservables
				.observeText(hobbyLabelText, SWT.Modify);
		IObservableValue modelHobbyLabelObserveValue = PojoObservables
				.observeValue(getModelObject(), Resource.NAME_PROPERTY);
		bindingContext.bindValue(hobbyLabelTextObserveTextObserveWidget,
				modelHobbyLabelObserveValue, null, null);

	}

}
