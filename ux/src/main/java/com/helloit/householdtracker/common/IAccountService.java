package com.helloit.householdtracker.common;

import org.jetbrains.annotations.NotNull;

/**
 */
public interface IAccountService {

    CreationOutcomes createAccount(@NotNull String userName, @NotNull String password, @NotNull String retypedPassword);

    boolean authenticate(@NotNull String userName, @NotNull String password);

    enum CreationOutcomes {
        SUCCESS,
        RETYPED_PASSWORD_DO_NOT_MATCH,
        EXISTING_ACCOUNT
    }

}
