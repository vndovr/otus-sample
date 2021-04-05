package by.radchuk.otus.account;

import java.math.BigDecimal;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import by.radchuk.otus.system.UserPrincipal;
import io.quarkus.hibernate.orm.panache.Panache;

@ApplicationScoped
public class AccountService {

  @Inject
  UserPrincipal userPrincipal;

  /**
   * Returns the amount of money on the account
   * 
   * @return
   */
  public AmountDto getAmount() {
    Account account = Optional.ofNullable((Account) Account.findById(userPrincipal.getLogin()))
        .orElseGet(() -> new Account(userPrincipal.getLogin(), BigDecimal.valueOf(0)));
    return new AmountDto(account.getAmount());
  }

  /**
   * Adds the money to the account (or substruct)
   * 
   * @param dto
   * @return
   */
  public AmountDto updateAmount(AmountDto dto) {
    Account account = Optional.ofNullable((Account) Account.findById(userPrincipal.getLogin()))
        .orElseGet(() -> new Account(userPrincipal.getLogin(), BigDecimal.valueOf(0)));
    account.setAmount(account.getAmount().add(dto.getAmount()));
    Account.persist(account);
    Panache.getEntityManager().flush();
    return new AmountDto(account.getAmount());
  }

}
