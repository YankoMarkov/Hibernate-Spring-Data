package softuni.gamestore.utils;

import javax.validation.Validator;

public class Validation {
	
	public static Validator getValidator() {
		return javax.validation.Validation.buildDefaultValidatorFactory().getValidator();
	}
}
