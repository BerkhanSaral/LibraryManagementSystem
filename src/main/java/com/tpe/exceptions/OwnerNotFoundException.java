package com.tpe.exceptions;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(String s) {
        super(s);
    }
}
