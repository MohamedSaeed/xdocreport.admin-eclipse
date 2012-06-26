package fr.opensagres.eclipse.forms.conversion;

import java.util.Date;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class StringToDateValidator implements IValidator {
	private final StringToDateConverter converter;

	/**
	 * @param converter
	 */
	public StringToDateValidator(StringToDateConverter converter) {
		this.converter = converter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.databinding.validation.IValidator#validate(java.lang
	 * .Object)
	 */
	public IStatus validate(Object value) {
		if (value instanceof String && ((String) value).trim().length() == 0) {
			return Status.OK_STATUS;
		}
		Object convertedValue = converter.convert(value);
		// The StringToDateConverter returns null if it can't parse the date.
		if (convertedValue == null) {
			return ValidationStatus.error(getErrorMessage());
		}

		return Status.OK_STATUS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.internal.databinding.validation.WrappedConverterValidator
	 * #getErrorMessage()
	 */
	protected String getErrorMessage() {
		// // FIXME We need to use the information from the
		// // converter, not use another instance of DateConversionSupport.
		// FormatUtil util = new FormatUtil();
		// StringBuffer samples = new StringBuffer();
		// for (int formatterIdx = 1; formatterIdx < util.numFormatters() - 2;
		// formatterIdx++) {
		// samples.append('\'');
		// samples.append(util.format(sampleDate, formatterIdx));
		//			samples.append("', "); //$NON-NLS-1$
		// }
		// samples.append('\'');
		// samples.append(util.format(sampleDate, 0));
		// samples.append('\'');
		// return BindingMessages.getString(BindingMessages.EXAMPLES)
		//				+ ": " + samples + ",..."; //$NON-NLS-1$//$NON-NLS-2$
		return "Date is invalid. Must follow the pattern '"
				+ converter.getPattern() + "'.";
	}
	//
	// private static class FormatUtil extends DateConversionSupport {
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * org.eclipse.core.internal.databinding.conversion.DateConversionSupport
	// * #numFormatters()
	// */
	// protected int numFormatters() {
	// return super.numFormatters();
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * org.eclipse.core.internal.databinding.conversion.DateConversionSupport
	// * #format(java.util.Date)
	// */
	// protected String format(Date date) {
	// return super.format(date);
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * org.eclipse.core.internal.databinding.conversion.DateConversionSupport
	// * #format(java.util.Date, int)
	// */
	// protected String format(Date date, int formatterIdx) {
	// return super.format(date, formatterIdx);
	// }
	// }
}
