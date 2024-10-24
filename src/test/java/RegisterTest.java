import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requestPOJOs.RegisterUser;

public class RegisterTest {
    private RegisterUser user;
    private String accessToken;
    private Response response;
    @Before
    public void setUp() {
        accessToken = null;
        user = DataGenerator.getRandomRegisterUser();
    }
    @After
    public void tearDown() {
        accessToken = UserClient.getAccessTokenWithoutBearer(response);
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
