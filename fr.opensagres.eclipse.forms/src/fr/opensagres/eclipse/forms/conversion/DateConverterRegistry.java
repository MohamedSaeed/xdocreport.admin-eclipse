package fr.opensagres.eclipse.forms.conversion;

import java.util.HashMap;
import java.util.Map;

public class DateConverterRegistry {

	private static final Map<String, StringToDateConverter> stringToDateConvertersCache = new HashMap<String, StringToDateConverter>();
	private static final Map<String, DateToStringConverter> dateTotringConvertersCache = new HashMap<String, DateToStringConverter>();

	public static StringToDateConverter getStringToDateConverter(String pattern) {
		StringToDateConverter converter = stringToDateConvertersCache
				.get(pattern);
		if (converter == null) {
			synchronized (stringToDateConvertersCache) {
				converter = new StringToDateConverter(pattern);
				stringToDateConvertersCache.put(pattern, converter);
			}
		}
		return converter;
	}

	public static DateToStringConverter getDateToStringConverter(String pattern) {
		DateToStringConverter converter = dateTotringConvertersCache
				.get(pattern);
		if (converter == null) {
			synchronized (dateTotringConvertersCache) {
				converter = new DateToStringConverter(pattern);
				dateTotringConvertersCache.put(pattern, converter);
			}
		}
		return converter;
	}

}
