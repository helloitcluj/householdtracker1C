package com.helloit.householdtracker.common.entities;

import javax.persistence.*;
import java.util.Calendar;

/**
 */

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar date;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "accountId")
    private User account;

    public Expense() {
    }

    public Expense(final double amount, final Calendar date, final String description, final User account) {
        this.amount = amount;
        this.date = date;
        this.description = description;

        setAccount(account);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(final User account) {
        this.account = account;

        if (!account.getExpenses().contains(this)) {
            account.addExpense(this);
        }
    }
}
