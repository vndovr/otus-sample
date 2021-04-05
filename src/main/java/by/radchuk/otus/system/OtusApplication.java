package by.radchuk.otus.system;

import javax.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(title = "OTUS homework API", version = "1.0",
        contact = @Contact(name = "OTUS homework API Support",
            url = "https://www.facebook.com/vladimir.radchuk", email = "radchuk@hotmail.com"),
        license = @License(name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html")),
    components = @Components(securitySchemes = {@SecurityScheme(securitySchemeName = "default",
        type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.COOKIE, apiKeyName = "token",
        description = "Token that is returned after successfull login should be included in the cookie token for all requests to the API calls")}),
    security = {@SecurityRequirement(name = "default")})
public class OtusApplication extends Application {
}
