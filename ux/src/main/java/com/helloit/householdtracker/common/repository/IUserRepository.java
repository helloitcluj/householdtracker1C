package com.helloit.householdtracker.common.repository;

import com.helloit.householdtracker.common.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 */
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findOneByUserName(String userName);
}
