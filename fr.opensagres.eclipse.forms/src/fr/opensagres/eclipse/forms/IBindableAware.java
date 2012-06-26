package fr.opensagres.eclipse.forms;

import org.eclipse.core.databinding.DataBindingContext;

public interface IBindableAware {

	void bind();
	
	void onBind(DataBindingContext dataBindingContext);
	
}
