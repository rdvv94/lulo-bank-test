package co.com.lulobank.domain.usecase.transaction.rules.validateinterval;

import co.com.lulobank.domain.model.transaction.AccountTransaction;
import co.com.lulobank.domain.model.transaction.gateway.TransactionRepository;
import co.com.lulobank.domain.usecase.transaction.rules.PredicateTransactionLimits;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ValidateTransactionInterval implements PredicateTransactionLimits {

    private final TransactionRepository transactionRepository;

    public ValidateTransactionInterval(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean valid(Integer minutes, Integer transactionLimit, Integer accountId) {
        return transactionRepository.getTransactionsLastMinutes(minutes, accountId).size() >= transactionLimit;
    }

    public boolean valid(Integer minutes, Integer transactionLimit, AccountTransaction accountTransaction) {
        AtomicInteger cont = new AtomicInteger();
        var transactions = transactionRepository.getTransactionsLastMinutes(
                minutes, accountTransaction.getAccount().getAccountId()).stream().filter(t -> {

            if (Objects.equals(t.getAmount(), accountTransaction.getTransaction().getAmount())
                    && t.getMerchant().equals(accountTransaction.getTransaction().getMerchant())) {
                cont.getAndIncrement();
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        return transactions.size() >= transactionLimit;
    }
}
