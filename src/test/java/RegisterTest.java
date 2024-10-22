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
    public void registerValidUserReturnsOk() {
        response = UserClient.registerUser(user);

        UserChecks.check200AndSuccessTrue(response);
    }

    @Test
    public void register2ValidUsersReturnsForbidden() {
        response = UserClient.registerUser(user);
        Response response2 = UserClient.registerUser(user);

        UserChecks.check403AndSuccessFalse(response2);
    }

    @Test
    public void registerUserWithoutEmailReturnsForbidden() {
        user.setEmail(null);
        UserClient.registerUser(user);

        response = UserClient.registerUser(user);

        UserChecks.check403AndSuccessFalse(response);
    }

}
