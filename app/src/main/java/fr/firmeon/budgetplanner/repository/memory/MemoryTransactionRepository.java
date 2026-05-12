package fr.firmeon.budgetplanner.repository.memory;

import com.sun.jdi.request.DuplicateRequestException;
import fr.firmeon.budgetplanner.model.Transaction;
import fr.firmeon.budgetplanner.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class MemoryTransactionRepository implements TransactionRepository {

    protected final List<Transaction> transactions;

    public MemoryTransactionRepository() {
        this.transactions = new ArrayList<>();
    }

    public MemoryTransactionRepository(List<Transaction> transactions){
        this.transactions = List.copyOf(transactions);
    }

    @Override
    public void save(Transaction transaction) {
        boolean exists = existsById(transaction.getId());
        if (exists) throw new DuplicateRequestException("A transaction with this id already exists");

        transactions.add(transaction);
    }

    @Override
    public boolean existsById(int id) {
        return transactions.stream().anyMatch(trans -> trans.getId() == id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactions.stream().toList();
    }

    @Override
    public Transaction findById(int id) {
        Optional<Transaction> transaction = transactions.stream().filter(trans -> trans.getId() == id).findFirst();
        if (transaction.isEmpty()) throw new NoSuchElementException("No transaction with this id found");
        else return transaction.get();
    }
}
