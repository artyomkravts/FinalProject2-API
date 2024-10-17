import io.restassured.RestAssured;
import io.restassured.response.Response;
import requestPOJOs.RegisterUser;

import static io.restassured.RestAssured.given;

public class UserClient {
    public static Response registerUser(RegisterUser user) {
        return
                RestAssured.given()
                        .header("Content-Type", "application/json")
                        .and()
                        .body(user)
                        .when().log().all()
                        .post(Constants.BASE_URI + "/api/auth/register");
    }
}
