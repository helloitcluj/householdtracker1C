package com.helloit.householdtracker.ux.spring;

import com.helloit.householdtracker.common.IAccountService;
import com.helloit.householdtracker.common.entities.User;
import com.helloit.householdtracker.common.repository.IUserRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 */
public class AccountServiceTest {

    @Test
    public void mismatchedPassword() {
        final IUserRepository mockUserRepository = getMockedUserRepository();
        final IAccountService accountService = new AccountService(mockUserRepository);

        final IAccountService.CreationOutcomes outcome = accountService.createAccount("aron", "123", "1234");

        Assert.assertEquals("Mismatched password!", IAccountService.CreationOutcomes.RETYPED_PASSWORD_DO_NOT_MATCH, outcome);
    }

    @NotNull
    private IUserRepository getMockedUserRepository() {
        return new IUserRepository() {

            @Override
            public <S extends User> S save(S entity) {
                return null;
            }

            @Override
            public User findOne(Integer integer) {
                return null;
            }

            @Override
            public boolean exists(Integer integer) {
                return false;
            }

            @Override
            public List<User> findAll() {
                return null;
            }

            @Override
            public List<User> findAll(Sort sort) {
                return null;
            }

            @Override
            public Page<User> findAll(Pageable pageable) {
                return null;
            }

            @Override
            public List<User> findAll(Iterable<Integer> integers) {
                return null;
            }

            @Override
            public <S extends User> List<S> save(Iterable<S> entities) {
                return null;
            }

            @Override
            public long count() {
                return 0;
            }

            @Override
            public void delete(Integer integer) {

            }

            @Override
            public void delete(User entity) {

            }

            @Override
            public void delete(Iterable<? extends User> entities) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends User> S saveAndFlush(S entity) {
                return null;
            }

            @Override
            public void deleteInBatch(Iterable<User> entities) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public User getOne(Integer integer) {
                return null;
            }


        };
    }

}
