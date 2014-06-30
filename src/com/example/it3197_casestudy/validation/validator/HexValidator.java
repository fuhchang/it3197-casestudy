package com.example.it3197_casestudy.validation.validator;

import java.util.regex.Pattern;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.validation.AbstractValidator;

import android.content.Context;


public class HexValidator extends AbstractValidator {

    private static final Pattern HEX_PATTERN = Pattern.compile("^(#|)[0-9A-Fa-f]+$");
    private static final int DEFAULT_ERROR_MESSAGE_RESOURCE = R.string.validator_hex;

    public HexValidator(Context c) {
        super(c, DEFAULT_ERROR_MESSAGE_RESOURCE);
    }

    public HexValidator(Context c, int errorMessageRes) {
        super(c, errorMessageRes);
    }

    @Override
    public boolean isValid(String text) {
        return HEX_PATTERN.matcher(text).matches();
    }
}
