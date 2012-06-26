package fr.opensagres.eclipse.forms.conversion;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.databinding.conversion.IConverter;

public class DateToStringConverter implements IConverter {

	private final String pattern;
	private final DateFormat formatter;

	public DateToStringConverter(String pattern) {
		this.pattern = pattern;
		this.formatter = new SimpleDateFormat(pattern);
	}

	public Object convert(Object source) {
		if (source != null)
			return format((Date) source);
		return ""; //$NON-NLS-1$
	}

	public String getPattern() {
		return pattern;
	}

	private Object format(Date source) {
		return formatter.format(source);
	}

	public Object getFromType() {
		return Date.class;
	}

	public Object getToType() {
		return String.class;
	}

}
