package fr.opensagres.eclipse.forms.conversion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.databinding.conversion.IConverter;

public class StringToDateConverter implements IConverter {

	private final String pattern;
	private final DateFormat formatter;

	public StringToDateConverter(String pattern) {
		this.pattern = pattern;
		this.formatter = new SimpleDateFormat(pattern);
	}

	public String getPattern() {
		return pattern;
	}

	public Object convert(Object source) {
		return parse(source.toString());
	}

	private Object parse(String source) {
		try {
			return formatter.parse(source);
		} catch (ParseException e) {
			return null;
		}
	}

	public Object getFromType() {
		return String.class;
	}

	public Object getToType() {
		return Date.class;
	}

}
