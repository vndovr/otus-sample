package by.radchuk.otus.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;


@QuarkusTest
@Slf4j
public class UserResourceTest {

  @Test
  public void testGetUsers() {
    Response response = given().when().get("/users").andReturn();
    log.info("Response: {}", response.body().asString());
    given().when().get("/users").then().statusCode(200).body(is(
        "[{\"firstName\":\"Vladimir\",\"id\":1,\"lastName\":\"Radchuk\",\"login\":\"vndovr\"}]"));
  }

}
