package com.helloit.householdtracker.common.repository;

import com.helloit.householdtracker.common.entities.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Integer> {

}