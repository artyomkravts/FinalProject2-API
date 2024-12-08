import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.experimental.UtilityClass;
import requestPOJOs.LoginUser;
import requestPOJOs.Order;
import requestPOJOs.RegisterUser;

import static io.restassured.RestAssured.given;

@UtilityClass
public class UserClient {

    @Step("Register user")
    public Response registerUser(RegisterUser user) {
        return
                RestAssured.given().log().all()
                        .contentType(ContentType.JSON)
                        .and()
                        .body(user)
                        .when()
                        .post(Constants.BASE_URI + Constants.REGISTER_PATH);
    }

    @Step("Log in user")
    public Response logInUser(LoginUser loginUser) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(loginUser)
                .when()
                .post(Constants.BASE_URI + Constants.LOGIN_PATH);
    }

    @Step("Patch user data")
    public Response patchUserData(RegisterUser user, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .and()
                .body(user)
                .when()
                .patch(Constants.BASE_URI  + Constants.USER_PATH);
    }

    @Step("Delete user")
    public Response deleteUser(String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .delete(Constants.BASE_URI + Constants.USER_PATH);
    }

    @Step("Get access token without 'Bearer ' part")
    public String getAccessTokenWithoutBearer(Response response) {
        String accessToken = response.then().log().all()
                .extract()
                .path("accessToken");
        if (accessToken != null) {
            return accessToken.replace("Bearer ", "");
        } else return null;
    }

    @Step("Get ingredients")
    public Response getIngredients() {
        return given().log().all()
                .when()
                .get(Constants.BASE_URI + Constants.INGREDIENTS_PATH);
    }

    @Step("Create order")
    public Response createOrder(Order order, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .contentType(ContentType.JSON)
                .and()
                .body(order)
                .when()
                .post(Constants.BASE_URI + Constants.ORDERS_PATH);
    }

    @Step("Get order by access token")
    public Response getOrder(String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken)
                .when()
                .get(Constants.BASE_URI + Constants.ORDERS_PATH);
    }
}
