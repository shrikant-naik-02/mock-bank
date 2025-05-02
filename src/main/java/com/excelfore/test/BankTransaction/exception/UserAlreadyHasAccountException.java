package com.excelfore.test.BankTransaction.exception;

public class UserAlreadyHasAccountException extends RuntimeException {
    public UserAlreadyHasAccountException(String message) {
        super(message);
    }
}
