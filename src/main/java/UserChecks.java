import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import requestPOJOs.RegisterUser;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserChecks {
    @Step("Check 200 and Success True")
    static void check200AndSuccessTrue(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("success", Matchers.equalTo(true));
    }

    @Step("Check 403 and success false")
    public static void check403AndSuccessFalse(Response response) {
        response.then().log().all()
                .statusCode(HTTP_FORBIDDEN)
                .and()
                .body("success", Matchers.equalTo(false));
    }

    @Step("Check 401 and success false")
    public static void check401AndSuccessFalse(Response response) {
        response.then().log().all()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("success", Matchers.equalTo(false));
    }

    @Step("Check 200 and returns access token")
    public static void check200AndReturnsAccessToken(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("accessToken", Matchers.notNullValue());
    }

    @Step("Check 200 and changed email and name")
    public static void check200AndChangedEmailAndName(Response response, String email, String name) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("user.email", equalTo(email))
                .and()
                .body("user.name", equalTo(name));
    }

    @Step("Check 500 Internal Server Error")
    public static void check500(Response response) {
        response.then().log().all()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step("Check 400 error and error message: ids must be provided")
    public static void check400AndErrorMessageIdsMustBeProvided(Response response) {
        response.then().log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", Matchers.containsString("must be provided"));
    }

    @Step("Check status 200, success: true, and no user credentials")
    public static void check200SuccessTrueAndNoUserCreds(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("success", Matchers.equalTo(true))
                .and()
                .body("order.owner.email", Matchers.nullValue())
                .and()
                .body("order.owner.name", Matchers.nullValue());
    }

    @Step("Check 200 and user credentials are correct")
    public static void check200AndUserCreds(Response response, RegisterUser user) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("order.owner.email", Matchers.equalTo(user.getEmail()))
                .and()
                .body("order.owner.name", Matchers.equalTo(user.getName()));
    }

    @Step("Check 200 and list of orders")
    public static void check200AndListOfOrders(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("orders", Matchers.notNullValue());
    }
}
