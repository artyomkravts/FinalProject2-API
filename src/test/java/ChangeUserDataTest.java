import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import requestPOJOs.LoginUser;
import requestPOJOs.RegisterUser;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.CoreMatchers.*;

public class ChangeUserDataTest {
    private static RegisterUser user;
    private static String accessToken;

    // Правила привязаны к тестовому классу, поэтому проверки с коллектором неудобно выносить в другие классы
    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() {
        Response response = UserClient.registerUser(DataGenerator.getRandomRegisterUser());

        accessToken = UserClient.getAccessTokenWithoutBearer(response);
    }

    @After
    public void tearDown() {
        Allure.parameter("user", user.toString());
        Allure.parameter("accessToken", accessToken);

        UserClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Patch user (all fields sent) successful")
    @Description("Positive test checks 200 and that email and name changed")
    public void patchUserAllFieldsReturnsOkAndChangedEmailAndName() {
        user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.patchUserData(user, accessToken);

        // Использую Soft Assertion, потому что нужно проверить больше 1 поля
        logTestStep("Check 1) status code 200; 2) email matches user data; 3) name matches user data");
        collector.checkThat("Status code is incorrect", response.getStatusCode(), is(HTTP_OK));
        collector.checkThat("Email is incorrect", response.path("user.email"), is(user.getEmail()));
        collector.checkThat("Name is incorrect", response.path("user.name"), is(user.getName()));
    }

    @Test
    @DisplayName("Patch user (all fields sent) successful")
    @Description("Positive test checks 200 and that password changed")
    public void patchUserAllFieldsReturnsOkAndChangedPassword() {
        user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.patchUserData(user, accessToken);

        logTestStep("Check 1) status code 200; 2) response message - success: true");
        collector.checkThat("Patch user data failed", response.getStatusCode(), is(HTTP_OK));
        collector.checkThat("Patch user data failed", response.path("success"), is(true));

        Response response1 = UserClient.logInUser(new LoginUser(user.getEmail(), user.getPassword()));

        logTestStep("Check 1) status code 200; 2) response message - success: true");
        collector.checkThat("Log In user failed", response.getStatusCode(), is(HTTP_OK));
        collector.checkThat("Log In user failed", response.path("success"), is(true));
    }

    @Test
    @DisplayName("Patch user (all fields sent) without authorization failed")
    @Description("Negative test checks 401 and response - success: false")
    public void patchUserNoAuthReturnsUnauthorized() {
        user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.patchUserData(user, "");

        UserChecks.check401AndSuccessFalse(response);
    }

    @Step("{message}")
    private void logTestStep(String message) {
        Allure.step(message);
    }

}
