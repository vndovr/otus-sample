package by.radchuk.otus.billing;

import java.math.BigDecimal;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
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
  @Transactional(TxType.REQUIRES_NEW)
  long createTransaction(String creditAccount, String debitAccount, String orderId,
      BigDecimal amount) {
    Transaction transaction = new Transaction();
    transaction.setCreditAccount(creditAccount);
    transaction.setDebitAccount(debitAccount);
    transaction.setOrderId(orderId);
    transaction.setAmount(amount);
    transaction.setState(Transaction.State.NEW);
    Transaction.persist(transaction);
    Panache.getEntityManager().flush();
    return transaction.id;
  }

  /**
   * Updates the state of the transaction
   * 
   * @param transactionId
   * @param state
   */
  @Transactional(TxType.REQUIRES_NEW)
  void updateState(long transactionId, Transaction.State state) {
    Transaction transaction = Transaction.findById(transactionId);
    transaction.setState(state);
    Transaction.persist(transaction);
  }
}
