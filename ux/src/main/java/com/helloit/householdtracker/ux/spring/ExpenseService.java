package com.helloit.householdtracker.ux.spring;

import com.helloit.householdtracker.common.entities.Expense;
import com.helloit.householdtracker.common.entities.User;
import com.helloit.householdtracker.common.repository.IExpenseRepository;
import com.helloit.householdtracker.common.service.IExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 */

@Service
public class ExpenseService implements IExpenseService {

    private final IExpenseRepository expenseRepository;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    @Autowired
    public ExpenseService(final IExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    @Override
    public Expense save(final String date, final double amount,
                        final String description, final User account) {

        final Expense expense = new Expense(amount, convert(date), description, account);

        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> findAllByUserId(final User account) {

        return expenseRepository.findByAccount(account);
    }

    @Override
    public Expense getByIdAndAccountId(final Integer expenseId, final User account) {
        Expense result = expenseRepository.findOne(expenseId);

        return (result != null && result.getAccount().getId().equals(account.getId())) ? result : null;
    }

    private Calendar convert(String dateAsString) {

        Calendar result = null;

        try {
            final Date date = formatter.parse(dateAsString);
            result = Calendar.getInstance();
            result.setTime(date);
        } catch (final ParseException ignored) {
            //ignored: returning null in this case
        }

        return result;
    }
}
