import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import requestPOJOs.LoginUser;
import requestPOJOs.RegisterUser;

public class LoginTest {

    private RegisterUser user;

    @Before
    public void setUp() {
        user = DataGenerator.getRandomRegisterUser();

        UserClient.registerUser(user);
    }

    @Test
    public void loginValidUserReturnsOkAndAccessToken() {
        LoginUser loginUser = new LoginUser(user.getEmail(), user.getPassword());

        Response response = UserClient.logInUser(loginUser);

        UserChecks.check200AndReturnsAccessToken(response);
    }

    @Test
    public void loginInvalidUserReturns401AndSuccessFalse() {
        Faker f = new Faker();
        String randomString = f.letterify("?????");
        LoginUser loginUser = new LoginUser(randomString + user.getEmail(), randomString + user.getPassword());

        Response response = UserClient.logInUser(loginUser);

        UserChecks.check401AndSuccessFalse(response);
    }

}
