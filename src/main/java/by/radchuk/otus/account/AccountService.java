package by.radchuk.otus.account;

import java.math.BigDecimal;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import by.radchuk.otus.system.exception.NotEnoughMoneyException;
import by.radchuk.otus.system.exception.ObjectNotFoundException;
import io.quarkus.hibernate.orm.panache.Panache;

@ApplicationScoped
@Transactional
public class AccountService {

  /**
   * Returns the amount of money on the account
   * 
   * @return
   */
  public AmountDto getAmount(String userId) {
    Account account = Optional.ofNullable((Account) Account.findById(userId))
        .orElseThrow(ObjectNotFoundException::new);
    return new AmountDto(account.getAmount());
  }

  /**
   * Adds the money to the account (or substruct)
   * 
   * @param userId
   * @param dto
   * @return
   */
  public AmountDto updateAmount(String userId, AmountDto dto) {
    Account account = Optional.ofNullable((Account) Account.findById(userId))
        .orElseThrow(ObjectNotFoundException::new);
    account.setAmount(account.getAmount().add(dto.getAmount()));
    Account.persist(account);
    Panache.getEntityManager().flush();
    return new AmountDto(account.getAmount());
  }

  /**
   * Creates new account for user
   * 
   * @param userId
   * @return
   */
  public void createAccount(String userId) {
    Account account = new Account();
    account.setAmount(BigDecimal.ZERO);
    account.setUserId(userId);
    account.setVersion(0);
    Account.persist(account);
  }

  /**
   * Creates new account for user
   * 
   * @param userId
   * @return
   */
  public void deleteAccount(String userId) {
    Account.deleteById(userId);
  }

  /**
   * Moves money from one account to another
   * 
   * @param creditAccount
   * @param debitAccount
   * @param amount
   * @return
   */
  public void transfer(String creditAccount, String debitAccount, BigDecimal amount) {
    Account accountFrom = Optional.ofNullable((Account) Account.findById(creditAccount))
        .orElseThrow(ObjectNotFoundException::new);
    Account accountTo = Optional.ofNullable((Account) Account.findById(debitAccount))
        .orElseThrow(ObjectNotFoundException::new);
    accountFrom.setAmount(accountFrom.getAmount().add(amount.negate()));
    if (accountFrom.getAmount().compareTo(BigDecimal.ZERO) == -1) {
      throw new NotEnoughMoneyException();
    }
    accountTo.setAmount(accountTo.getAmount().add(amount));
    Account.persist(accountFrom, accountTo);
  }

}
