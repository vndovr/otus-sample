package by.radchuk.otus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Starter implements QuarkusApplication {

  public static void main(String... args) {
    Quarkus.run(Starter.class, args);
  }

  @Override
  public int run(String... args) {
    Quarkus.waitForExit();
    return 10;
  }

}