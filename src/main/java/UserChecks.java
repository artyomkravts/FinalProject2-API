import io.qameta.allure.Step;
import io.restassured.response.Response;

import lombok.experimental.UtilityClass;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import requestPOJOs.RegisterUser;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.is;

@UtilityClass
public class UserChecks {
    @Step("Check 200 and Success True")
    public void check200AndSuccessTrue(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("success", Matchers.equalTo(true));
    }

    @Step("Check 403 and success false")
    public void check403AndSuccessFalse(Response response) {
        response.then().log().all()
                .statusCode(HTTP_FORBIDDEN)
                .and()
                .body("success", Matchers.equalTo(false));
    }

    @Step("Check 401 and success false")
    public void check401AndSuccessFalse(Response response) {
        response.then().log().all()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("success", Matchers.equalTo(false));
    }

    @Step("Check 200 and returns access token")
    public void check200AndReturnsAccessToken(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("accessToken", Matchers.notNullValue());
    }

    @Step("Check 500 Internal Server Error")
    public void check500(Response response) {
        response.then().log().all()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step("Check 400 error and error message: ids must be provided")
    public void check400AndErrorMessageIdsMustBeProvided(Response response) {
        response.then().log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", Matchers.containsString("must be provided"));
    }

    @Step("Check status 200, success: true, and no user credentials")
    public void check200SuccessTrueAndNoUserCreds(Response response) {
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
    public void check200AndUserCreds(Response response, RegisterUser user) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("order.owner.email", Matchers.equalTo(user.getEmail()))
                .and()
                .body("order.owner.name", Matchers.equalTo(user.getName()));
    }

    @Step("Check 200 and list of orders")
    public void check200AndListOfOrders(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("orders", Matchers.notNullValue());
    }

    @Step("Check 1) status code 200; 2) email matches user data; 3) name matches user data")
    public void softCheckStatusOkAndEmailAndNameMatchUserData(Response response, String email, String name) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(HTTP_OK, response.getStatusCode(), "Status code is incorrect"),
                () -> Assertions.assertEquals(email, response.path("user.email"), "Email is incorrect"),
                () -> Assertions.assertEquals(name, response.path("user.name"), "Name is incorrect")
        );
    }

    @Step("Check 1) status code 200; 2) response message - success: true")
    public void softCheck200AndStatusSuccess(Response response) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(HTTP_OK, response.getStatusCode(), "Status code is not 200"),
                () -> Assertions.assertTrue(response.path("success"), "Success =/= true")
        );
    }
}
