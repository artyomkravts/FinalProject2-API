import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import requestPOJOs.RegisterUser;

import java.util.Locale;

public class RegisterTest {
    private RegisterUser user;
    @Before
    public void setUp() {
        Faker eng = new Faker(Locale.US);
        var email = eng.internet().emailAddress();
        var password = eng.bothify("?????#####???###");
        var name = eng.name().firstName();
        user = new RegisterUser(email, password, name);
    }

    @Test
    public void registerValidRandomUserReturnsOk() {
        Response response = UserClient.registerUser(user);

        UserChecks.check200AndSuccessTrue(response);
    }

    @Test
    public void register2ValidRandomUsersReturnsForbidden() {
        UserClient.registerUser(user);
        Response response = UserClient.registerUser(user);

        UserChecks.check403AndSuccessFalse(response);
    }

    @Test
    public void registerInvalidUserReturnsError() {
        user.setEmail(null);
        UserClient.registerUser(user);

        Response response = UserClient.registerUser(user);

        UserChecks.check403AndSuccessFalse(response);
    }



}
