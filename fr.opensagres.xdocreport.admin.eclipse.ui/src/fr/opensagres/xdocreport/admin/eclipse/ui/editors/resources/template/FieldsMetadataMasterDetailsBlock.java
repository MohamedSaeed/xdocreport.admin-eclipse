package fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.template;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.rap.singlesourcing.SingleSourcingUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import fr.opensagres.eclipse.forms.ModelMasterDetailsBlock;
import fr.opensagres.xdocreport.admin.eclipse.core.Platform;
import fr.opensagres.xdocreport.admin.eclipse.core.Repository;
import fr.opensagres.xdocreport.admin.eclipse.ui.Activator;
import fr.opensagres.xdocreport.admin.eclipse.ui.editors.resources.ResourceEditorInput;
import fr.opensagres.xdocreport.admin.eclipse.ui.internal.Messages;
import fr.opensagres.xdocreport.admin.eclipse.ui.viewers.FieldsMetadataContentProvider;
import fr.opensagres.xdocreport.admin.eclipse.ui.viewers.FieldsMetadataLabelProvider;
import fr.opensagres.xdocreport.admin.eclipse.ui.viewers.FieldsMetadataTreeModel;
import fr.opensagres.xdocreport.remoting.resources.domain.BinaryData;
import fr.opensagres.xdocreport.remoting.resources.domain.Resource;
import fr.opensagres.xdocreport.remoting.resources.domain.ResourceHelper;
import fr.opensagres.xdocreport.remoting.resources.services.ResourcesService;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadataXMLSerializer;

public class FieldsMetadataMasterDetailsBlock extends
		ModelMasterDetailsBlock<Resource> {

	private static final Integer ADD_BUTTON_INDEX = 1;
	private static final Integer REMOVE_BUTTON_INDEX = 2;

	private FieldMetadataDetailsPage documentDetailsPage;
	private TableViewer viewer;
	private Button removeButton;

	public FieldsMetadataMasterDetailsBlock(FieldsMetadataPage hobbiesPage) {
		super(hobbiesPage);
		this.documentDetailsPage = new FieldMetadataDetailsPage();
	}

	@Override
	protected void onCreateUI(final IManagedForm managedForm, Composite parent) {

		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setText(Messages.TemplateResourceEditor_DocumentsPage_DocumentsMasterDetailsBlock_title); //$NON-NLS-1$
		section.setDescription(Messages.TemplateResourceEditor_DocumentsPage_DocumentsMasterDetailsBlock_desc); //$NON-NLS-1$
		section.marginWidth = 10;
		section.marginHeight = 5;

		Composite client = toolkit.createComposite(section, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		client.setLayout(layout);

		Table hobbiesTable = toolkit.createTable(client, SWT.MULTI);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 20;
		gd.widthHint = 100;
		hobbiesTable.setLayoutData(gd);
		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit, client);

		createButtons(toolkit, client);

		section.setClient(client);

		final SectionPart spart = new SectionPart(section);
		managedForm.addPart(spart);
		viewer = new TableViewer(hobbiesTable);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event
						.getSelection();
				if (selection.size() == 1) {
					managedForm.fireSelectionChanged(spart,
							event.getSelection());
				}
				removeButton.setEnabled(true);
			}
		});
		viewer.setContentProvider(FieldsMetadataContentProvider.getInstance());
		viewer.setLabelProvider(FieldsMetadataLabelProvider.getInstance());
	}

	private void createButtons(FormToolkit toolkit, Composite parent) {
		GridData gd;
		Composite buttonsContainer = toolkit.createComposite(parent);
		gd = new GridData(GridData.FILL_VERTICAL);
		buttonsContainer.setLayoutData(gd);
		buttonsContainer.setLayout(createButtonsLayout());

		SelectionAdapter listener = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.widget.getData() == ADD_BUTTON_INDEX) {
					handleAddButton();
				} else if (e.widget.getData() == REMOVE_BUTTON_INDEX) {
					handleRemoveButton();
				}
			}
		};

		Button addButton = toolkit.createButton(buttonsContainer,
				Messages.addButton_label, SWT.PUSH); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		addButton.setData(ADD_BUTTON_INDEX);
		addButton.setLayoutData(gd);
		addButton.setEnabled(true);
		addButton.addSelectionListener(listener);

		removeButton = toolkit.createButton(buttonsContainer,
				Messages.removeButton_label, SWT.PUSH); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		removeButton.setData(REMOVE_BUTTON_INDEX);
		removeButton.setLayoutData(gd);
		removeButton.setEnabled(false);
		removeButton.addSelectionListener(listener);

		SingleSourcingUtils.FormToolkit_paintBordersFor(toolkit,
				buttonsContainer);
	}

	protected void handleAddButton() {
		Resource document = new Resource();
		document.setName("New Document");
		viewer.add(document);
		viewer.setSelection(new StructuredSelection(document));
	}

	protected void handleRemoveButton() {
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		if (!selection.isEmpty()) {
			Resource document = null;
			Object[] hobbies = selection.toArray();
			for (int i = 0; i < hobbies.length; i++) {
				document = (Resource) hobbies[i];				
				viewer.remove(document);
			}
			viewer.refresh();
		}
	}

	protected GridLayout createButtonsLayout() {
		GridLayout layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		return layout;
	}

	@Override
	public void onBind(DataBindingContext dataBindingContext) {

		Resource fieldsMetada = ResourceHelper
				.findFieldsMetadataFromTemplate(getModelObject());

		Repository repository = ((ResourceEditorInput) getEditor()
				.getEditorInput()).getRepository();

		try {
			ResourcesService resourcesService = Platform.getRepositoryManager()
					.getResourcesService(repository);
			BinaryData binaryData = resourcesService.download(fieldsMetada
					.getIdNotNull());

			FieldsMetadata fieldsMetadata = FieldsMetadataXMLSerializer
					.getInstance().load(
							new ByteArrayInputStream(binaryData.getContent()));

			FieldsMetadataTreeModel treeModel = new FieldsMetadataTreeModel(
					fieldsMetadata);
			viewer.setInput(treeModel);
		} catch (Throwable e) {
			e.printStackTrace();
			Status status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0,
					"Repository connection", e);
			ErrorDialog.openError(viewer.getControl().getShell(),
					"RepositoryService Error", e.getMessage(), status);
		}
	}

	public IDetailsPage getPage(Object key) {
		return documentDetailsPage;
	}

	public Object getPageKey(Object object) {
		return null;
	}

}
