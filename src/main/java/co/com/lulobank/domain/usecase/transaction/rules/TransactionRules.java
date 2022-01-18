package co.com.lulobank.domain.usecase.transaction.rules;

import co.com.lulobank.domain.model.account.AccountResponse;
import co.com.lulobank.domain.model.transaction.AccountTransaction;

public class TransactionRules {

    public static boolean validateCard(AccountResponse.AccountData accountData,
                                       PredicateAccount predicate) {
        return predicate.valid(accountData);
    }

    public static boolean validateLimit(AccountTransaction accountTransaction, PredicateTransaction predicate) {
        return predicate.valid(accountTransaction);
    }

    public static boolean transactionLimits(Integer minutes, Integer transactionLimit, Integer accountId,
                                            PredicateTransactionLimits predicate) {
        return predicate.valid(minutes, transactionLimit, accountId);
    }

    public static boolean transactionLimits(Integer minutes, Integer transactionLimit,
                                            AccountTransaction accountTransaction,
                                            PredicateTransactionLimits predicate) {
        return predicate.valid(minutes, transactionLimit, accountTransaction);

    }
}
