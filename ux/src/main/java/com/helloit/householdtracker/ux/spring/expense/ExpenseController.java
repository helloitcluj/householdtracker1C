package com.helloit.householdtracker.ux.spring.expense;


import com.helloit.householdtracker.common.entities.Expense;
import com.helloit.householdtracker.common.entities.User;
import com.helloit.householdtracker.common.service.IAccountService;
import com.helloit.householdtracker.common.service.IExpenseService;
import com.helloit.householdtracker.ux.common.SecurityFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(path = "expense")
public class ExpenseController {

    private static final Logger LOGGER = LogManager.getLogger(ExpenseController.class);

    @Autowired
    private IExpenseService expenseService;

    @Autowired
    private IAccountService accountService;

    @RequestMapping(path = "create", method = RequestMethod.POST)
    public
    @ResponseBody
    void create(final HttpSession session, final String date, final double amount, final String description) {
        final String userName = (String) session.getAttribute(SecurityFilter.CURRENT_PRINCIPAL_TAG);
        final User user = accountService.find(userName);

        expenseService.save(date, amount, description, user.getId());
    }

    @RequestMapping(path = "findAll", method = RequestMethod.POST)
    public
    @ResponseBody
    List<Expense> findAll(final HttpSession session) {
        final String userName = (String) session.getAttribute(SecurityFilter.CURRENT_PRINCIPAL_TAG);
        final User user = accountService.find(userName);

        return expenseService.findAllByUserId(user.getId());
    }

}