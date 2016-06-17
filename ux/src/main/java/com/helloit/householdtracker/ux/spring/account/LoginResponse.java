package com.helloit.householdtracker.ux.spring.account;

/**
 */
public class LoginResponse {

    private final Kind kind;
    private final String message;

    public LoginResponse(final Kind kind, final String message) {
        this.kind = kind;
        this.message = message;
    }

    public LoginResponse(final Kind kind) {
        this(kind, null);
    }

    public Kind getKind() {
        return kind;
    }

    public String getMessage() {
        return message;
    }

    public enum Kind {
        SUCCESS,
        ERROR

    }
}
