import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import requestPOJOs.RegisterUser;

public class RegisterTest {
    private RegisterUser user;
    private Response response;

    @BeforeEach
    public void setUp() {
        user = DataGenerator.getRandomRegisterUser();
    }

    @AfterEach
    public void tearDown() {
        Allure.parameter("user", user);

        String accessToken = UserClient.getAccessTokenWithoutBearer(response);
        if (accessToken != null) {
            UserClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Register valid user successful")
    @Description("Positive test checks 200 and that response returns message - success: true")
    public void registerValidUserReturnsOk() {
        response = UserClient.registerUser(user);

        UserChecks.check200AndSuccessTrue(response);
    }

    @Test
    @DisplayName("Register 2 valid users forbidden")
    @Description("Negative test checks 403 and that response returns error message - success: false")
    public void register2ValidUsersReturnsForbidden() {
        response = UserClient.registerUser(user);
        Response response2 = UserClient.registerUser(user);

        UserChecks.check403AndSuccessFalse(response2);
    }

    @Test
    @DisplayName("Register user without email forbidden")
    @Description("Negative test checks 403 and that response returns error message - success: false")
    public void registerUserWithoutEmailReturnsForbidden() {
        user.setEmail(null);
        UserClient.registerUser(user);

        response = UserClient.registerUser(user);

        UserChecks.check403AndSuccessFalse(response);
    }
}
