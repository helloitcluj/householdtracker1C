package com.helloit.householdtracker.ux.spring.expense;

import java.util.Calendar;

/**
 */
public class ExpenseDTO {

    private Integer id;

    private double amount;

    private Calendar date;

    private String description;

    private Integer accountId;

    public ExpenseDTO() {
    }

    public ExpenseDTO(final double amount, final Calendar date, final String description, final Integer accountId) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.accountId = accountId;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccount(final Integer accountId) {
        this.accountId = accountId;
    }

}
