package com.helloit.householdtracker.ux.spring;

import com.helloit.householdtracker.common.entities.User;
import com.helloit.householdtracker.common.repository.IUserRepository;
import com.helloit.householdtracker.common.service.IAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

/**
 */

@Service
public class AccountService implements IAccountService {

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    private final IUserRepository userRepository;

    @Autowired
    public AccountService(final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CreationOutcomes createAccount(@NotNull final String userName, @NotNull final String password, @NotNull final String retypedPassword) {
        CreationOutcomes result;

        if (password.equals(retypedPassword)) {
            final User user = new User();
            user.setUserName(userName);
            user.setPassword(password);

            try {
                userRepository.save(user);
                result = CreationOutcomes.SUCCESS;
            } catch (JpaSystemException ex) {
                boolean found = false;
                for (Throwable exceptionChain = ex; exceptionChain != null && !found; exceptionChain = exceptionChain.getCause()) {
                    found = exceptionChain instanceof ConstraintViolationException;
                }

                if (found) {
                    result = CreationOutcomes.EXISTING_ACCOUNT;
                } else {
                    throw ex;
                }

            }
        } else {
            result = CreationOutcomes.RETYPED_PASSWORD_DO_NOT_MATCH;
        }

        return result;
    }

    @Override
    public boolean authenticate(@NotNull final String userName, @NotNull final String password) {
        final User user = userRepository.findOneByUserName(userName);

        return user != null && password.equals(user.getPassword());
    }

    @Override
    public User find(final String userName) {

        return userRepository.findOneByUserName(userName);
    }
}
