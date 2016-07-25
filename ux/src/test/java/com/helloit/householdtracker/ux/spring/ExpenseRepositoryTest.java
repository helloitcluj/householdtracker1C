package com.helloit.householdtracker.ux.spring;

import com.helloit.householdtracker.common.entities.Expense;
import com.helloit.householdtracker.common.entities.User;
import com.helloit.householdtracker.common.repository.IExpenseRepository;
import com.helloit.householdtracker.common.repository.IUserRepository;
import com.helloit.householdtracker.tools.SchemaManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

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

    private SchemaManager schemaManager;

    private User testUser;

    @Before
    public void setup() {
        schemaManager = new SchemaManager();
        schemaManager.recreateSchema();

        final User user = new User();
        user.setUserName("Test");
        user.setPassword("1");

        testUser = userRepository.save(user);
    }


    @Test
    @Transactional
    public void basicTest() {

        final Calendar now = Calendar.getInstance();
        final Expense expense = new Expense(35.2, now, "Profi city", testUser);
        final Expense saved = expenseRepository.save(expense);

        Assert.assertEquals("The first id created should be 0", new Integer(0), saved.getId());

        final List<Expense> expenses = expenseRepository.findByAccount(testUser);
        Assert.assertEquals("We should have one expense", 1, expenses.size());

        final Expense firstAndOnlyExpense = expenses.get(0);
        final User relatedAccount = firstAndOnlyExpense.getAccount();

        final Set<Expense> expensesAgain = relatedAccount.getExpenses();
        Assert.assertEquals("We should have one expense", 1, expensesAgain.size());


        Assert.assertEquals("Account should be the test one", testUser.getId(), relatedAccount.getId());
    }

    @Test
    public void jdbcReadTest() throws SQLException, ClassNotFoundException {

        //needed for JDBC drivers written prior JDBC 4.0
        final String driverClassName = schemaManager.getDriverClassName();
        Class.forName(driverClassName);

        final String connectionString = schemaManager.getConnectionString();
        try (final Connection connection = DriverManager.getConnection(connectionString)) {
            try (final Statement statement = connection.createStatement()) {
                try (final ResultSet resultSet = statement.executeQuery("SELECT id, userName, password FROM users")) {
                    System.out.println("=======================");
                    while (resultSet.next()) {
                        final String userName = resultSet.getString("userName");
                        final int id = resultSet.getInt("id");
                        final String password = resultSet.getString("password");

                        System.out.printf("%d; username : %s; password: %s; \n", id, userName, password);
                    }
                    System.out.println("=======================");
                }
            }
        }

    }

    @Test
    public void jdbcWriteTest() throws SQLException, ClassNotFoundException {

        //needed for JDBC drivers written prior JDBC 4.0
        final String driverClassName = schemaManager.getDriverClassName();
        Class.forName(driverClassName);

        final String connectionString = schemaManager.getConnectionString();
        try (final Connection connection = DriverManager.getConnection(connectionString)) {
            try (final PreparedStatement statement = connection.prepareStatement("INSERT INTO users (userName, password) VALUES (?, ?)")) {
                statement.setString(1, "Jo");
                statement.setString(2, "1");
                statement.execute();
            }
        }

        jdbcReadTest();

        final List<User> all = userRepository.findAll();

        Assert.assertEquals("Should be 2 items!", 2, all.size());
    }

    @Test
    public void jdbcDeleteTest() throws SQLException, ClassNotFoundException {

        //needed for JDBC drivers written prior JDBC 4.0
        final String driverClassName = schemaManager.getDriverClassName();
        Class.forName(driverClassName);

        final String connectionString = schemaManager.getConnectionString();
        try (final Connection connection = DriverManager.getConnection(connectionString)) {
            try (final Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM users");
            }
        }

        jdbcReadTest();

        final List<User> all = userRepository.findAll();

        Assert.assertEquals("Should be 0 items!", 0, all.size());
    }

}
