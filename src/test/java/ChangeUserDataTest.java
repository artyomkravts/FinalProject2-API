import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requestPOJOs.LoginUser;
import requestPOJOs.RegisterUser;

import static io.restassured.RestAssured.given;

public class ChangeUserDataTest {
    private static RegisterUser user;
    private static String accessToken;
    @Before
    public void setUp() {
        Response response = UserClient.registerUser(DataGenerator.getRandomRegisterUser());

        accessToken = UserClient.getAccessTokenWithoutBearer(response);
    }

    @After
    public void tearDown() {
        UserClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Patch user (email, password, name) and check that email and name changed")
    public void patchUserAllFieldsReturnsOkAndChangedEmailName() {
        user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.patchUserData(user, accessToken);

        UserChecks.check200AndChangedEmailName(response, user.getEmail(), user.getName());
    }

    @Test
    @DisplayName("Patch user (email, password, name) and check that password changed")
    public void patchUserAllFieldsReturnsOkAndChangedPassword() {
        user = DataGenerator.getRandomRegisterUser();
        Response response = UserClient.patchUserData(user, accessToken);
        UserChecks.check200AndSuccessTrue(response);

        Response response1 = UserClient.logInUser(new LoginUser(user.getEmail(), user.getPassword()));
        UserChecks.check200AndSuccessTrue(response1);
    }

    @Test
    @DisplayName("Patch user (email, password, name) without authorization and check 401")
    public void patchUserNoAuthReturnsUnauthorized() {
        user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.patchUserData(user, "");

        UserChecks.check401AndSuccessFalse(response);
    }

}
