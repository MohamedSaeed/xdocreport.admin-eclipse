package fr.opensagres.eclipse.forms.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.AggregateValidationStatus;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IEditorInput;

import fr.opensagres.eclipse.forms.IBindableAware;
import fr.opensagres.eclipse.forms.validation.ValidationFormEditorSupport;

public abstract class ModelFormEditor<EditorInput extends IEditorInput, Model>
		extends AbstractFormEditor<EditorInput> {

	public static final String SHARED_DATABINDING_CONTEXT_ID = "___SharedDBC";

	protected final class DirtyFlag implements IChangeListener {

		private boolean dirty = false;

		public boolean isDirty() {
			return dirty;
		}

		public void setDirty(boolean dirty) {
			if (this.dirty != dirty) {
				this.dirty = dirty;
				editorDirtyStateChanged();
			}
		}

		public void handleChange(ChangeEvent event) {
			setDirty(true);
		}
	}

	private ChangeTracker changeTracker = new ChangeTracker();
	private boolean initializeBinding;
	private final List<IBindableAware> pagesAlreadyBounded;
	private final Map<String, DataBindingContextWrapper> dataBindingContextCahe;

	private Model model;
	private DirtyFlag dirtyFlag;

	public ModelFormEditor() {
		super();
		this.initializeBinding = false;
		this.pagesAlreadyBounded = new ArrayList<IBindableAware>();
		this.dataBindingContextCahe = new HashMap<String, DataBindingContextWrapper>();
	}

	@Override
	protected void onAfterAddPages() {
		super.onAfterAddPages();

		// // Load model
		reload();
		dirtyFlag = new DirtyFlag();

		changeTracker.addChangeListener(dirtyFlag);
	}

	private void reload() {
		model = onLoad(getEditorInput());
	}

	protected Model getModelObject() {
		return (Model) model;
	}

	protected synchronized void bind(IBindableAware page,
			DataBindingContext dataBindingContext) {
		try {
			DataBindingContextWrapper wrapper = getWrapper(dataBindingContext);
			wrapper.saveCurrentBindings();
			changeTracker.setEnabled(false);
			page.onBind(dataBindingContext);
			if (!pagesAlreadyBounded.contains(page)) {
				pagesAlreadyBounded.add(page);
			}
			changeTracker.trackTargetObservables(dataBindingContext,
					wrapper.getCurrentBindings());
		} finally {
			changeTracker.setEnabled(true);
		}
	}

	private DataBindingContextWrapper getWrapper(
			DataBindingContext dataBindingContext) {
		Collection<DataBindingContextWrapper> wrappers = dataBindingContextCahe
				.values();
		for (DataBindingContextWrapper wrapper : wrappers) {
			if (wrapper.getDataBindingContext().equals(dataBindingContext)) {
				return wrapper;
			}
		}
		return null;
	}

	/**
	 * Implement onLoad to retrieve the model object by the editor input object.
	 */
	protected abstract Model onLoad(EditorInput input);

	public DataBindingContext getDatabindingContext(String databindingContentId) {
		DataBindingContextWrapper wrapper = dataBindingContextCahe
				.get(databindingContentId);
		if (wrapper == null) {
			wrapper = new DataBindingContextWrapper(new DataBindingContext());
			addStatusSupport(wrapper.getDataBindingContext());
			dataBindingContextCahe.put(databindingContentId, wrapper);
		}
		return wrapper.getDataBindingContext();
	}

	public boolean isInitializeBinding() {
		return initializeBinding;
	}

	@Override
	public boolean isDirty() {
		if (dirtyFlag.isDirty()) {
			return true;
		}
		return super.isDirty();
	}

	@Override
	protected void onSave(IProgressMonitor monitor) {
		Collection<DataBindingContextWrapper> wrappers = dataBindingContextCahe
				.values();
		for (DataBindingContextWrapper wrapper : wrappers) {
			wrapper.getDataBindingContext().updateModels();
		}
		model = onSave(getModelObject(), monitor);
		onReload(getModelObject());

		for (DataBindingContextWrapper wrapper : wrappers) {
			wrapper.getDataBindingContext().updateTargets();
			wrapper.getDataBindingContext().dispose();
		}

		for (IBindableAware page : pagesAlreadyBounded) {
			page.bind();
		}
		dirtyFlag.setDirty(false);
	}

	public void setForceDirty(boolean dirty) {
		if (dirtyFlag != null) {
			dirtyFlag.setDirty(dirty);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		Collection<DataBindingContextWrapper> wrappers = dataBindingContextCahe
				.values();
		for (DataBindingContextWrapper wrapper : wrappers) {
			wrapper.getDataBindingContext().dispose();
		}
		dataBindingContextCahe.clear();
		pagesAlreadyBounded.clear();
		dirtyFlag = null;
		model = null;
	}

	protected void onReload(Model modelObject) {

	}

	private void addStatusSupport(final DataBindingContext dataBindingContext) {
		AggregateValidationStatus aggregateStatus = ValidationFormEditorSupport
				.createAggregateValidationStatus(this, dataBindingContext);
		changeTracker.addObservable(aggregateStatus);
	}

	protected abstract Model onSave(Model modelObject, IProgressMonitor monitor);

	public void contributeToToolbar(IToolBarManager manager) {

	}
}
