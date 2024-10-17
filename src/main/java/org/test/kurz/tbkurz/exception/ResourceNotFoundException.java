package org.test.kurz.tbkurz.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String currency) {
        super(String.format("Currency %s was not found.", currency));
    }

}
