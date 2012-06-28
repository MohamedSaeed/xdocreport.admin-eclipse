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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import fr.opensagres.eclipse.forms.editor.ModelDetailsPage;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;

public class FieldMetadataDetailsPage extends ModelDetailsPage<Resource> {

	private Text fieldNameText;
	private Control fieldDescriptionText;

	@Override
	protected void onCreateUI(Composite parent) {

		TableWrapLayout layout = new TableWrapLayout();
		layout.topMargin = 5;
		layout.leftMargin = 5;
		layout.rightMargin = 2;
		layout.bottomMargin = 2;
		parent.setLayout(layout);

		FormToolkit toolkit = getManagedForm().getToolkit();

		Section fieldDetailSection = toolkit.createSection(parent,
				Section.DESCRIPTION | Section.TITLE_BAR);
		fieldDetailSection.marginWidth = 10;
		fieldDetailSection
				.setText(Messages.TemplateResourceEditor_FieldsMetadataPage_FieldMetadataDetailsPage_title); //$NON-NLS-1$
		fieldDetailSection
				.setDescription(Messages.TemplateResourceEditor_FieldsMetadataPage_FieldMetadataDetailsPage_desc); //$NON-NLS-1$

		TableWrapData td = new TableWrapData(TableWrapData.FILL,
				TableWrapData.TOP);
		td.grabHorizontal = true;
		fieldDetailSection.setLayoutData(td);

		Composite client = toolkit.createComposite(fieldDetailSection);
		fieldDetailSection.setClient(client);

		// Create generic content
		createBodyContent(toolkit, client);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, client);
	}

	private void createBodyContent(FormToolkit toolkit, Composite parent) {
		GridLayout glayout = new GridLayout();
		glayout.numColumns = 2;
		parent.setLayout(glayout);

		// Field name
		toolkit.createLabel(
				parent,
				Messages.TemplateResourceEditor_FieldsMetadataPage_FieldMetadataDetailsPage_fieldName_label);
		fieldNameText = toolkit.createText(parent, "", SWT.SINGLE);
		fieldNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Field description
		toolkit.createLabel(
				parent,
				Messages.TemplateResourceEditor_FieldsMetadataPage_FieldMetadataDetailsPage_fieldDescription_label);
		fieldDescriptionText = toolkit.createText(parent, "", SWT.SINGLE);
		fieldDescriptionText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

	}

	public void onBind(DataBindingContext bindingContext) {

		// Name binding
		IObservableValue hobbyLabelTextObserveTextObserveWidget = SWTObservables
				.observeText(fieldNameText, SWT.Modify);
		IObservableValue modelHobbyLabelObserveValue = PojoObservables
				.observeValue(getModelObject(), Resource.NAME_PROPERTY);
		bindingContext.bindValue(hobbyLabelTextObserveTextObserveWidget,
				modelHobbyLabelObserveValue, null, null);

	}

}
