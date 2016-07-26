DROP SCHEMA PUBLIC CASCADE;

CREATE CACHED TABLE users (
  id       INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  userName VARCHAR(30) NOT NULL UNIQUE,
  password VARCHAR(30) NOT NULL
);

CREATE CACHED TABLE expenses (
  id          INTEGER IDENTITY PRIMARY KEY,
  amount      DOUBLE,
  date        TIMESTAMP,
  description VARCHAR(30),
  accountId   INTEGER NOT NULL
);

ALTER TABLE expenses ADD FOREIGN KEY (accountId) REFERENCES users (id);

CREATE CACHED TABLE expenses_expense_categories (
  expenseId         INTEGER,
  expenseCategoryId INTEGER
);


CREATE CACHED TABLE expense_categories (
  id          INTEGER IDENTITY PRIMARY KEY,
  description VARCHAR(30)
);

ALTER TABLE expenses_expense_categories ADD PRIMARY KEY (expenseId, expenseCategoryId);
ALTER TABLE expenses_expense_categories ADD FOREIGN KEY (expenseId) REFERENCES expenses (id);
ALTER TABLE expenses_expense_categories ADD FOREIGN KEY (expenseCategoryId) REFERENCES expense_categories (id);

INSERT INTO expense_categories (id, description) VALUES (0, 'all');
INSERT INTO expense_categories (id, description) VALUES (1, 'food');
INSERT INTO expense_categories (id, description) VALUES (2, 'cloth');
