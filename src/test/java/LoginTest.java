import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import requestPOJOs.LoginUser;
import requestPOJOs.RegisterUser;

import java.util.Locale;

public class LoginTest {

    private LoginUser loginUser;
    private String email;
    private String password;

    @Before
    public void setUp() {
        RegisterUser user;
        Faker eng = new Faker(Locale.US);
        email = eng.internet().emailAddress();
        password = eng.bothify("?????#####???###");
        user = new RegisterUser(email, password, eng.name().firstName());
        UserClient.registerUser(user);
    }

    @Test
    public void loginValidUserReturnsOkAndAccessToken() {
        LoginUser loginUser = new LoginUser(email, password);

        Response response = UserClient.logInUser(loginUser);

        UserChecks.check200AndReturnsAccessToken(response);
    }

    @Test
    public void loginInvalidUserReturns401AndSuccessFalse() {
        Faker f = new Faker();
        String randomString = f.letterify("?????");
        LoginUser loginUser = new LoginUser(randomString + email, randomString + password);

        Response response = UserClient.logInUser(loginUser);

        UserChecks.check401AndSuccessFalse(response);
    }

}
