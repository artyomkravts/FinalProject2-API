import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static java.net.HttpURLConnection.*;

public class UserChecks {
    static void check200AndSuccessTrue(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .assertThat().body("success", Matchers.equalTo(true));
    }

    public static void check403AndSuccessFalse(Response response) {
        response.then().log().all()
                .statusCode(HTTP_FORBIDDEN)
                .and()
                .body("success", Matchers.equalTo(false));
    }

    public static void check401AndSuccessFalse(Response response) {
        response.then().log().all()
                .statusCode(HTTP_UNAUTHORIZED)
                .and()
                .body("success", Matchers.equalTo(false));
    }

    public static void check200AndReturnsAccessToken(Response response) {
        response.then().log().all()
                .statusCode(HTTP_OK)
                .and()
                .extract()
                .path("accessToken");
    }
}
