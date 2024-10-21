import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import requestPOJOs.LoginUser;
import requestPOJOs.RegisterUser;

import static io.restassured.RestAssured.given;

public class UserClient {
    public static Response registerUser(RegisterUser user) {
        return
                RestAssured.given()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(user)
                        .when().log().all()
                        .post(Constants.BASE_URI + "/api/auth/register");
    }

    public static Response logInUser(LoginUser loginUser) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(loginUser)
                .when()
                .post(Constants.BASE_URI + "/api/auth/login");
    }

    public static Response patchUserData(RegisterUser user, String accessToken) {
        return given()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .patch(Constants.BASE_URI  + "/api/auth/user");
    }
}
