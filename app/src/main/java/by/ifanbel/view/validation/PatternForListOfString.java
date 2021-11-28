package by.ifanbel.view.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ListOfStringValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PatternForListOfString {
	String regexp();

	String message() default "Incorrect value";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
