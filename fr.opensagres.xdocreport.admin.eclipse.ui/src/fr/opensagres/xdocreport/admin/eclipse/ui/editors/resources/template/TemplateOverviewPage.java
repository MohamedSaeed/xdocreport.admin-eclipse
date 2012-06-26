package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.template;

import org.eclipse.rap.singlesourcing.SingleSourcingUtils;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.OverviewPage;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.ResourceEditor;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.ImageResources;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;
import fr.opensagres.xdocreport.remoting.resources.domain.ResourceType;

public class TemplateOverviewPage extends OverviewPage  {

	public TemplateOverviewPage(ResourceEditor editor, ResourceType resourceType) {
		super(editor, resourceType);
	}

	@Override
	protected void createRightSection(FormToolkit toolkit, Composite right) {
		createContentSection(toolkit, right);
	}

	private void createContentSection(FormToolkit toolkit, Composite parent) {
		Section section = toolkit.createSection(parent, Section.TITLE_BAR);
		section.setText(Messages.TemplateResourceEditor_TemplateOverviewPage_TemplateResourceContent_title);
		TableWrapData data = new TableWrapData(TableWrapData.FILL_GRAB);
		section.setLayoutData(data);

		Composite sbody = toolkit.createComposite(section);
		section.setClient(sbody);

		Composite container = createStaticSectionClient(toolkit, section);

		FormText text = createClient(container,
				Messages.TemplateResourceEditor_TemplateOverviewPage_TemplateResourceContent_content,
				toolkit);
		text.setImage("documents_page",
				ImageResources.getImage(ImageResources.IMG_DOCUMENT_16));
		text.setImage("fieldsmetadata_page",
				ImageResources.getImage(ImageResources.IMG_DOCUMENT_16));
		section.setClient(container);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, sbody);
	}

}
