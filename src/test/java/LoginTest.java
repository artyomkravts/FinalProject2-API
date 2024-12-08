import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import io.restassured.response.Response;
import requestPOJOs.LoginUser;
import requestPOJOs.RegisterUser;

public class LoginTest {

    private RegisterUser user;
    private String accessToken;

    @BeforeEach
    public void setUp() {
        user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.registerUser(user);

        accessToken = UserClient.getAccessTokenWithoutBearer(response);
    }

    @AfterEach
    public void tearDown() {
        Allure.parameter("user", user);
        Allure.parameter("accessToken", accessToken);

        UserClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Login valid user successful")
    @Description("Positive test checks 200 and that response returns access token")
    public void loginValidUserReturnsOkAndAccessToken() {
        LoginUser loginUser = new LoginUser(user.getEmail(), user.getPassword());

        Response response = UserClient.logInUser(loginUser);

        UserChecks.check200AndReturnsAccessToken(response);
    }

    @Test
    @DisplayName("Login invalid user failed")
    @Description("Negative test checks 401 and that response returns error message - success: false")
    public void loginInvalidUserReturns401AndSuccessFalse() {
        Faker f = new Faker();
        String randomString = f.letterify("?????");
        LoginUser loginUser = new LoginUser(randomString + user.getEmail(), randomString + user.getPassword());

        Response response = UserClient.logInUser(loginUser);

        UserChecks.check401AndSuccessFalse(response);
    }

}
