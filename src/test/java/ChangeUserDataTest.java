import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import requestPOJOs.LoginUser;
import requestPOJOs.RegisterUser;

public class ChangeUserDataTest {
    private static RegisterUser user;
    private static String accessToken;

    // Правила привязаны к тестовому классу, поэтому проверки с коллектором неудобно выносить в другие классы -- для JUnit 4
//    @Rule
//    public ErrorCollector collector = new ErrorCollector();


    @BeforeEach
    public void setUp() {
        Response response = UserClient.registerUser(DataGenerator.getRandomRegisterUser());

        accessToken = UserClient.getAccessTokenWithoutBearer(response);
    }

    @AfterEach
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
        UserChecks.softCheckStatusOkAndEmailAndNameMatchUserData(response, user.getEmail(), user.getName());
    }

    @Test
    @DisplayName("Patch user (all fields sent) successful")
    @Description("Positive test checks 200 and that password changed")
    public void patchUserAllFieldsReturnsOkAndChangedPassword() {
        user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.patchUserData(user, accessToken);

        UserChecks.softCheck200AndStatusSuccess(response);

        Response response1 = UserClient.logInUser(new LoginUser(user.getEmail(), user.getPassword()));

        UserChecks.softCheck200AndStatusSuccess(response1);
    }

    @Test
    @DisplayName("Patch user (all fields sent) without authorization failed")
    @Description("Negative test checks 401 and response - success: false")
    public void patchUserNoAuthReturnsUnauthorized() {
        user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.patchUserData(user, "");

        UserChecks.check401AndSuccessFalse(response);
    }

}
