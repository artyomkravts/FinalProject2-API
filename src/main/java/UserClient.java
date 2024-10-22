import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import requestPOJOs.LoginUser;
import requestPOJOs.RegisterUser;

import static io.restassured.RestAssured.given;

public class UserClient {

    public static Response registerUser(RegisterUser user) {
        return
                RestAssured.given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(user)
                        .when()
                        .post(Constants.BASE_URI + Constants.REGISTER_PATH);
    }

    public static Response logInUser(LoginUser loginUser) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(loginUser)
                .when()
                .post(Constants.BASE_URI + Constants.LOGIN_PATH);
    }

    public static Response patchUserData(RegisterUser user, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .patch(Constants.BASE_URI  + Constants.USER_PATH);
    }

    public static Response deleteUser(String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete(Constants.BASE_URI + Constants.USER_PATH);
    }

    @Step("Get access token without 'Bearer ' part")
    static String getAccessTokenWithoutBearer(Response response) {
        String accessToken = response.then().log().all()
                .extract()
                .path("accessToken");
        if (accessToken != null) {
            return accessToken.replace("Bearer ", "");
        } else return null;
    }
}
