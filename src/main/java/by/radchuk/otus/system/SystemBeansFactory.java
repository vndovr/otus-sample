package by.radchuk.otus.system;

import java.net.InetAddress;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import org.apache.commons.lang3.StringUtils;
import io.quarkus.runtime.configuration.ProfileManager;
import lombok.SneakyThrows;

@Singleton
public class SystemBeansFactory {

  @Produces
  @Hostname
  @Singleton
  @SneakyThrows
  public String buildHostname() {
    String profile = ProfileManager.getActiveProfile();

    if ("dev".equals(profile) || "test".equals(profile))
      return "auth-profile-account-event-order-bill-email";

    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("win")) {
      return StringUtils.defaultString(System.getenv("COMPUTERNAME"));
    } else if (os.contains("nix") || os.contains("nux") || os.contains("mac os x")) {
      return StringUtils.defaultString(System.getenv("HOSTNAME"));
    } else {
      return StringUtils.defaultString(InetAddress.getLocalHost().getHostName());
    }
  }
}
