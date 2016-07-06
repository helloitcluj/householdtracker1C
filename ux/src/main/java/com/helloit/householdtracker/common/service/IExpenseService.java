package com.helloit.householdtracker.common.service;

import com.helloit.householdtracker.common.entities.Expense;

/**
 */
public interface IExpenseService {
    Expense save(String date, double amount, String description, Integer accountId);
}
