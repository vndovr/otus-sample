package by.radchuk.otus.system;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.flywaydb.core.Flyway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class MigrationService {

  @Inject
  Flyway flyway;

  public void checkMigration() {
    log.info("Flyway version: {}", flyway.info().current().getVersion().toString());
  }
}
