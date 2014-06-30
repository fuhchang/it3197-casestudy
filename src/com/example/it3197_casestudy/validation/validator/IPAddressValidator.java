package com.example.it3197_casestudy.validation.validator;

import java.util.regex.Pattern;

import android.content.Context;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.validation.AbstractValidator;

/**
 * Validates whether ip digits is between 0-255 digits, has only numbers and is the correct format
 * 
 * @author Dixon D'Cunha (Exikle)
 */
public class IPAddressValidator extends AbstractValidator {

	/**
	 * IP Addresses format
	 */
	private static final String IPADDRESS_STRING_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	/**
	 * Compiled pattern for the format
	 */
	private final Pattern IPADDRESS_PATTERN = Pattern
			.compile(IPADDRESS_STRING_PATTERN);

	/**
	 * Default message if none specified
	 */
	private static final int DEFAULT_ERROR_MESSAGE_RESOURCE = R.string.validator_ip;

	public IPAddressValidator(Context c) {
		super(c, DEFAULT_ERROR_MESSAGE_RESOURCE);
	}

	@Override
	public boolean isValid(String ip) {
		return IPADDRESS_PATTERN.matcher(ip).matches();
	}
}
