package com.techouts.util;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.primefaces.validate.ClientValidator;

@FacesValidator("numberValidation")
public class CustomNumberValidator implements Validator, ClientValidator {
	private static final Logger LOG = Logger
			.getLogger(CustomNumberValidator.class);

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return "numberValidation";
	}

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if (value == null)
			return;

		try {
			Integer units = (Integer) value;
			LOG.info("Validator value :" + units);
			if (units.intValue() <= 0)
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Validation Error", value
								+ " is should be +ve Units"));
		} catch (Exception e) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Validation Error", value
							+ " is not a valid Units"));
		}

	}

}
