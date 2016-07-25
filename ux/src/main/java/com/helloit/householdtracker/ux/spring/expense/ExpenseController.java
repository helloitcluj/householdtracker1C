package com.helloit.householdtracker.ux.spring.expense;


import com.helloit.householdtracker.common.entities.Expense;
import com.helloit.householdtracker.common.entities.User;
import com.helloit.householdtracker.common.service.IAccountService;
import com.helloit.householdtracker.common.service.IExpenseService;
import com.helloit.householdtracker.ux.common.SecurityFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "expense")
public class ExpenseController {

    private static final Logger LOGGER = LogManager.getLogger(ExpenseController.class);

    @Autowired
    private IExpenseService expenseService;

    @Autowired
    private IAccountService accountService;

    @RequestMapping(path = "create", method = RequestMethod.POST)
    public void create(final HttpSession session, final String date, final double amount, final String description) {
        final User user = getCurrentUser(session);

        expenseService.save(date, amount, description, user);
    }

    @RequestMapping(path = "findAll", method = RequestMethod.POST)
    public List<ExpenseDTO> findAll(final HttpSession session) {
        final User user = getCurrentUser(session);

        final List<Expense> allByUserId = expenseService.findAllByUserId(user);

        final List<ExpenseDTO> result = allByUserId.stream()
                .map((item) -> new ExpenseDTO(item.getAmount(), item.getDate(), item.getDescription(), item.getAccount().getId()))
                .collect(Collectors.toList());

        return result;
    }

    @RequestMapping(path = "{expenseId}", method = RequestMethod.GET)
    public Expense getById(@PathVariable final Integer expenseId, final HttpSession session) {
        final User user = getCurrentUser(session);

        return expenseService.getByIdAndAccountId(expenseId, user);
    }

    private User getCurrentUser(HttpSession session) {
        final String userName = (String) session.getAttribute(SecurityFilter.CURRENT_PRINCIPAL_TAG);
        return accountService.find(userName);
    }

}