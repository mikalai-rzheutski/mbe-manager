package by.ifanbel.view.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Locale;

import static java.lang.System.lineSeparator;

/**
 * The class performs regexp validation of jsp List<String> which is absent in Spring Framework.
 */
public class ListOfStringValidator implements ConstraintValidator<PatternForListOfString, List<String>> {

	private String regexp;

	@Autowired
	MessageSource messageSource;

	public void initialize(PatternForListOfString pattern) {
		regexp = pattern.regexp();
	}

	public boolean isValid(List<String> values, ConstraintValidatorContext context) {
		StringBuilder errorMessages = new StringBuilder();
		String defaultMessage = context.getDefaultConstraintMessageTemplate();
		for (int i = 0; i < values.size(); i++) {
			if (!values.get(i)
					.replace(" ", "")
					.replace(",", ".")
					.matches(regexp)) {
				errorMessages.append(defaultMessage)
						.append(messageSource.getMessage("validation.inTheLayer", null, LocaleContextHolder.getLocale()))
						.append(i + 1)
						.append(lineSeparator());
			}
		}
		if (errorMessages.length() != 0) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(errorMessages.toString())
					.addConstraintViolation();
			return false;
		}
		return true;
	}

}