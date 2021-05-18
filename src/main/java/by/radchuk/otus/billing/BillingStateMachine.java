package by.radchuk.otus.billing;

import java.io.StringReader;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
enum BillingStateMachine {
  NEW {
    @Override
    BillingStateMachine next(Transaction t, BillingStateMachineContext context) {
      return PAY;
    }
  },
  PAY {
    @Override
    BillingStateMachine next(Transaction t, BillingStateMachineContext context) {
      try {
        context.getAccountClient().transfer(t.getCreditAccount(), t.getDebitAccount(),
            t.getAmount(), t.getId());
        return RESERVE;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        return PAY_ROLL;
      }
    }
  },
  RESERVE {
    @Override
    BillingStateMachine next(Transaction t, BillingStateMachineContext context) {
      try {
        context.getWarehouseClient().reserve(t.getId(),
            context.getJsonb().fromJson(t.getEvent(), InvoiceDto.class).getItems());
        return BOOK;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        return RESERVE_ROLL;
      }
    }
  },
  BOOK {
    @Override
    BillingStateMachine next(Transaction t, BillingStateMachineContext context) {
      try {
        context.getDeliveryClient()
            .reserve(
                context.getJsonb().fromJson(new StringReader(t.getEvent()), InvoiceDto.class)
                    .getDeliveryTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")),
                t.getId());
        return SUCC_MAIL;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        return BOOK_ROLL;
      }
    }
  },
  SUCC_MAIL {
    @Override
    BillingStateMachine next(Transaction t, BillingStateMachineContext context) {
      try {
        context.getNotificationClint().handleSuccessfull(
            context.getJsonb().fromJson(new StringReader(t.getEvent()), InvoiceDto.class));
        return FINAL;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        return SUCC_MAIL;
      }
    }
  },
  BOOK_ROLL {

    @Override
    BillingStateMachine next(Transaction t, BillingStateMachineContext context) {
      try {
        context.getDeliveryClient()
            .release(
                context.getJsonb().fromJson(new StringReader(t.getEvent()), InvoiceDto.class)
                    .getDeliveryTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")),
                t.getId());
        return RESERVE_ROLL;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        return BOOK_ROLL;
      }
    }

  },
  RESERVE_ROLL {
    @Override
    BillingStateMachine next(Transaction t, BillingStateMachineContext context) {
      try {
        context.getWarehouseClient().release(t.getId(),
            context.getJsonb().fromJson(t.getEvent(), InvoiceDto.class).getItems());
        return PAY_ROLL;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        return RESERVE_ROLL;
      }
    }
  },
  PAY_ROLL {
    @Override
    BillingStateMachine next(Transaction t, BillingStateMachineContext context) {
      try {
        context.getAccountClient().rollback(t.getId());
        return FAIL_MAIL;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        return PAY_ROLL;
      }
    }
  },
  FAIL_MAIL {
    @Override
    BillingStateMachine next(Transaction t, BillingStateMachineContext context) {
      try {
        context.getNotificationClint().handleFailure(
            context.getJsonb().fromJson(new StringReader(t.getEvent()), InvoiceDto.class));
        return FINAL;
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        return FAIL_MAIL;
      }
    }

  },
  FINAL {
    @Override
    BillingStateMachine next(Transaction t, BillingStateMachineContext context) {
      return FINAL;
    }
  };

  abstract BillingStateMachine next(Transaction t, BillingStateMachineContext context);
}
