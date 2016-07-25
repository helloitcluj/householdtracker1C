package com.helloit.householdtracker.common.service;

import com.helloit.householdtracker.common.entities.Expense;
import com.helloit.householdtracker.common.entities.User;

import java.util.List;

/**
 */
public interface IExpenseService {
    Expense save(String date, double amount, String description, User account);


    List<Expense> findAllByUserId(User account);

    Expense getByIdAndAccountId(Integer expenseId, User account);

}
