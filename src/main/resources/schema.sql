DROP TABLE transactions IF EXISTS;
DROP TABLE accounts IF EXISTS;

CREATE TABLE accounts (account_id int, available_limit double, active_card bit, PRIMARY KEY (account_id));
CREATE TABLE transactions (transaction_id int, merchant varchar(30), amount double, time_give timestamp, account_id int NOT NULL, PRIMARY KEY (transaction_id));
ALTER TABLE transactions ADD CONSTRAINT FKtransactio692136 FOREIGN KEY (account_id) REFERENCES accounts (account_id);
