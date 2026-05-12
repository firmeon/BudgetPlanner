package fr.firmeon.budgetplanner.repository;

import fr.firmeon.budgetplanner.model.Transaction;

import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);

    boolean existsById(int id);

    List<Transaction> findAll();
    Transaction findById(int id);
}
