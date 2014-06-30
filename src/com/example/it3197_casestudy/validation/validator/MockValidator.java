package com.example.it3197_casestudy.validation.validator;

import android.content.Context;

import com.example.it3197_casestudy.validation.AbstractValidator;

/**
 * Just a mock validator to use as a coding place holder. Always returns true.
 * 
 * @author Dixon D'Cunha (Exikle)
 */
public class MockValidator extends AbstractValidator {

	/**
	 * Sets the error as "NO ERROR"
	 */
	private static final String DEFAULT_ERROR_MESSAGE_RESOURCE = "NO ERROR";

	public MockValidator(Context c) {
		super(c, DEFAULT_ERROR_MESSAGE_RESOURCE);
	}

	@Override
	public boolean isValid(String value) {
		return true;
	}

}
