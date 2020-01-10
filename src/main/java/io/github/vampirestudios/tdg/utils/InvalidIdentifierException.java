package io.github.vampirestudios.tdg.utils;

public class InvalidIdentifierException extends RuntimeException {
    public InvalidIdentifierException(String string_1) {
        super(string_1);
    }

    public InvalidIdentifierException(String string_1, Throwable throwable_1) {
        super(string_1, throwable_1);
    }
}