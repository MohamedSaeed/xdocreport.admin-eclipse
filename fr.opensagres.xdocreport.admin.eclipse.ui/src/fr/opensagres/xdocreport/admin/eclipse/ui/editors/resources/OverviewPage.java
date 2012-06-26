package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources;

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
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.eclipse.forms.editor.ModelToolbarFormPage;
import fr.opensagres.xdocreport.admin.eclipse.core.Repository;
import fr.opensagres.xdocreport.admin.eclipse.ui.FormLayoutFactory;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;
import fr.opensagres.xdocreport.remoting.resources.domain.ResourceType;

public class OverviewPage extends ModelToolbarFormPage<Resource> {

	private static final String ID = "overview";

	private final ResourceType resourceType;
	private Text resourceIdText;
	private Text resourceNameText;

	public OverviewPage(ResourceEditor editor, ResourceType resourceType) {
		super(editor, ID, Messages.ResourceEditor_OverviewPage_title);
		this.resourceType = resourceType;
	}

	@Override
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {

		Composite body = managedForm.getForm().getBody();
		body.setLayout(FormLayoutFactory.createFormTableWrapLayout(true, 2));

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
		section.setDescription(getDescription());
		section.setText(Messages.ResourceEditor_OverviewPage_GeneralInfo_title);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		GridLayout glayout = new GridLayout();
		// glayout.horizontalSpacing = 10;
		glayout.numColumns = 2;
		sbody.setLayout(glayout);

		// ID
		toolkit.createLabel(
				sbody,
				Messages.ResourceEditor_OverviewPage_GeneralInfo_resourceId_label);
		resourceIdText = toolkit.createText(sbody, "", SWT.SINGLE);
		GridData resourceIdGridData = new GridData(GridData.FILL_HORIZONTAL);
		resourceIdGridData.widthHint = 150;
		resourceIdText.setLayoutData(resourceIdGridData);

		// Name
		toolkit.createLabel(
				sbody,
				Messages.ResourceEditor_OverviewPage_GeneralInfo_resourceName_label);
		resourceNameText = toolkit.createText(sbody, "", SWT.SINGLE);
		GridData resourceNameGridData = new GridData(GridData.FILL_HORIZONTAL);
		resourceNameGridData.widthHint = 150;
		resourceNameText.setLayoutData(resourceNameGridData);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);
	}

	private String getDescription() {
		switch (resourceType) {
		case DOCUMENT:
			return Messages.FileResourceEditor_OverviewPage_GeneralInfo_desc;
		case TEMPLATE:
			return Messages.TemplateResourceEditor_OverviewPage_GeneralInfo_desc;
		}
		return Messages.FolderResourceEditor_OverviewPage_GeneralInfo_desc;
	}

	public void onBind(DataBindingContext bindingContext) {

		// bind resource id
		IObservableValue resourceIdWidgetValue = SWTObservables.observeText(
				resourceIdText, SWT.Modify);
		IObservableValue resourceIdModelValue = PojoObservables.observeValue(
				getModelObject(), Resource.ID_PROPERTY);
		bindingContext.bindValue(resourceIdWidgetValue, resourceIdModelValue,
				null, null);

		// bind resource name
		IObservableValue resourceNameWidgetValue = SWTObservables.observeText(
				resourceNameText, SWT.Modify);
		IObservableValue resourceNameModelValue = PojoObservables.observeValue(
				getModelObject(), Resource.NAME_PROPERTY);
		bindingContext.bindValue(resourceNameWidgetValue,
				resourceNameModelValue, null, null);
	}
}
