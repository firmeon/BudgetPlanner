package fr.firmeon.budgetplanner.repository.memory;

import com.sun.jdi.request.DuplicateRequestException;
import fr.firmeon.budgetplanner.model.Transaction;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@ExtendWith(SoftAssertionsExtension.class)
class MemoryTransactionRepositoryTest {

    private MemoryTransactionRepository repo;

    @BeforeEach
    void setupBeforEach(){
        repo = new MemoryTransactionRepository();
    }

    @Test
    void shouldSaveNewTransaction(SoftAssertions softly){
        Transaction newTrans = new Transaction(1, 100, LocalDate.now(), "New test");

        repo.save(newTrans);

        softly.assertThat(repo.transactions.contains(newTrans)).isTrue();
    }

    @Test
    void shouldNotSaveNewTransaction(SoftAssertions softly){
        Transaction newTrans = new Transaction(1, 100, LocalDate.now(), "New test");
        repo.transactions.add(newTrans);

        softly.assertThatThrownBy(() -> repo.save(newTrans))
                        .isInstanceOf(DuplicateRequestException.class);
        softly.assertThat(repo.transactions.contains(newTrans)).isTrue();
    }

    @Test
    void shouldContainsExistngTransaction(SoftAssertions softly){
        Transaction newTrans = new Transaction(1, 100, LocalDate.now(), "New test");
        repo.transactions.add(newTrans);

        boolean exists = repo.existsById(1);

        softly.assertThat(exists).isTrue();
    }

    @Test
    void shouldNotContainsExistngTransaction(SoftAssertions softly){
        boolean exists = repo.existsById(1);

        softly.assertThat(exists).isFalse();
    }

    @Test
    void shouldGiveAllElements(SoftAssertions softly){
        Transaction t1 = new Transaction(1, 100, LocalDate.now(), "First");
        Transaction t2 = new Transaction(2, 100, LocalDate.now(), "Second");

        repo.transactions.add(t1);
        repo.transactions.add(t2);

        List<Transaction> transactions = repo.findAll();
        softly.assertThat(transactions.size()).isEqualTo(2);
        softly.assertThat(transactions.getFirst().getId()).isEqualTo(1);
        softly.assertThat(transactions.getLast().getId()).isEqualTo(2);
    }

    @Test
    void shouldGiveNoneElements(SoftAssertions softly){

        List<Transaction> transactions = repo.findAll();
        softly.assertThat(transactions.size()).isEqualTo(0);
        softly.assertThat(transactions).isEmpty();
    }

    @Test
    void shouldGiveCorrectTransaction(SoftAssertions softly){
        Transaction t1 = new Transaction(1, 100, LocalDate.now(), "First");
        Transaction t2 = new Transaction(2, 100, LocalDate.now(), "Second");

        repo.transactions.add(t1);
        repo.transactions.add(t2);

        Transaction trans = repo.findById(1);

        softly.assertThat(trans).isEqualTo(t1);
        softly.assertThat(trans.getId()).isEqualTo(1);
    }

    @Test
    void shouldNotGiveTransaction(SoftAssertions softly){
        softly.assertThatThrownBy(() -> repo.findById(1))
                .isInstanceOf(NoSuchElementException.class);
    }

}
