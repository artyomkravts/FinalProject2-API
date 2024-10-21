import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserChecks {
    @Step("Check 200 and Success True")
    static void check200AndSuccessTrue(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .assertThat().body("success", Matchers.equalTo(true));
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
                .extract()
                .path("accessToken");
    }

    @Step("Check 200 and changed email and name")
    public static void check200AndChangedEmailName(Response response, String email, String name) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .body("user.email", equalTo(email))
                .and()
                .body("user.name", equalTo(name));
    }
}
