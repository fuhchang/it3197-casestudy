package com.example.it3197_casestudy.validation;

public class ValidatorException extends java.lang.Exception {
    private static final long serialVersionUID = 1L;

    public ValidatorException() {
        super();
    }

    public ValidatorException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ValidatorException(String detailMessage) {
        super(detailMessage);
    }

    public ValidatorException(Throwable throwable) {
        super(throwable);
    }
}
