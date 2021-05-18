package by.radchuk.otus.billing;

import java.math.BigDecimal;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import io.quarkus.hibernate.orm.panache.Panache;

@ApplicationScoped
@Transactional
class BillingService {

  /**
   * Created audit record in transaction table
   * 
   * @param creditAccount
   * @param debitAccount
   * @param orderId
   * @param amount
   * @return
   */
  String createTransaction(String creditAccount, String debitAccount, String orderId,
      BigDecimal amount, String event) {
    Transaction transaction = new Transaction();
    transaction.setId(UUID.randomUUID().toString());
    transaction.setCreditAccount(creditAccount);
    transaction.setDebitAccount(debitAccount);
    transaction.setOrderId(orderId);
    transaction.setAmount(amount);
    transaction.setEvent(event);
    transaction.setState(BillingStateMachine.NEW);
    Transaction.persist(transaction);
    Panache.getEntityManager().flush();
    return transaction.getId();
  }


  /**
   * Returns the transaction by id
   * 
   * @param id
   * @return
   */
  Transaction getTransaction(String id) {
    return Transaction.findById(id);
  }

  /**
   * Updates the state of the transaction
   * 
   * @param transactionId
   * @param state
   */
  void updateState(String transactionId, BillingStateMachine state) {
    Transaction transaction = Transaction.findById(transactionId);
    transaction.setState(state);
    Transaction.persist(transaction);
  }
}
