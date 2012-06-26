package fr.opensagres.eclipse.forms.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;

public class DataBindingContextWrapper {

	private final DataBindingContext dataBindingContext;
	private final List<Binding> currentBindings;

	public DataBindingContextWrapper(DataBindingContext dataBindingContext) {
		this.dataBindingContext = dataBindingContext;
		this.currentBindings = new ArrayList<Binding>();
	}

	public DataBindingContext getDataBindingContext() {
		return dataBindingContext;
	}

	public void saveCurrentBindings() {
		for (Iterator<Binding> iterator = dataBindingContext.getBindings()
				.iterator(); iterator.hasNext();) {
			Binding binding = iterator.next();
			if (!currentBindings.contains(binding)) {
				currentBindings.add(binding);
			}
		}
	}

	public List<Binding> getCurrentBindings() {
		return currentBindings;
	}
}
