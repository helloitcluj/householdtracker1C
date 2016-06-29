package com.helloit.householdtracker.ux.spring;

import com.helloit.householdtracker.common.entities.Expense;
import com.helloit.householdtracker.common.entities.User;
import com.helloit.householdtracker.common.repository.IExpenseRepository;
import com.helloit.householdtracker.common.repository.IUserRepository;
import com.helloit.householdtracker.tools.SchemaManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Calendar;

/**
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, WebConfig.class})
@WebAppConfiguration
public class ExpenseRepositoryTest {
    @Autowired
    private IExpenseRepository expenseRepository;

    @Autowired
    private IUserRepository userRepository;

    private User testUser;

    @Before
    public void setup() {
        final SchemaManager schemaManager = new SchemaManager();
        schemaManager.recreateSchema();

        final User user = new User();
        user.setUserName("Test");
        user.setPassword("1");

        testUser = userRepository.save(user);
    }


    @Test
    public void basicTest() {

        final Calendar now = Calendar.getInstance();
        final Expense expense = new Expense(35.2, now, "Profi city", testUser.getId());
        final Expense saved = expenseRepository.save(expense);
    }

}
