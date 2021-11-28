package by.ifanbel.configurations.view;

import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The Converter is used for proper displaying of collection-data in jsp.
 */
public class StringToCollectionConverter implements Converter<String, Collection> {
	public Collection convert(String s) {
		Collection collection = new ArrayList<String>();
		collection.add(s);
		return collection;
	}
}
