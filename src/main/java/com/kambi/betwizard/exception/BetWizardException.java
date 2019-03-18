package com.kambi.betwizard.exception;

public class BetWizardException  extends RuntimeException {

    public BetWizardException(final String message) {
        super(message);
    }

    public BetWizardException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}

