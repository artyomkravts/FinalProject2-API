import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requestPOJOs.Order;
import requestPOJOs.RegisterUser;

import java.util.ArrayList;

public class CreateOrderTest {
    private static RegisterUser user;
    private static String accessToken;
    @Before
    public void setUp() {
            user = DataGenerator.getRandomRegisterUser();

            Response response = UserClient.registerUser(user);

            accessToken = UserClient.getAccessTokenWithoutBearer(response);
        }

    @After
    public void tearDown() {
        UserClient.deleteUser(accessToken);
    }

    @Test
    public void createOrderWithValidAuthTokenReturnsOkAndUserCreds() {
        Order order = DataGenerator.getRandomOrder();

        Response response = UserClient.createOrder(order, accessToken);

        UserChecks.check200AndUserCreds(response, user);
    }

    @Test
    public void createOrderWithoutAuthReturnsOkAndNoUserCreds() {
        Order order = DataGenerator.getRandomOrder();

        Response response = UserClient.createOrder(order, "");

        UserChecks.check200SuccessTrueAndNoUserCreds(response);
    }

    @Test
    public void createOrderWithoutIngredientsReturns400AndErrorMessage() {
        Order order = new Order();

        Response response = UserClient.createOrder(order, accessToken);

        UserChecks.check400AndErrorMessageIdsMustBeProvided(response);
    }

    @Test
    public void createOrderWithoutAuthWithoutIngredientsReturns400AndErrorMessage() {
        Order order = new Order();

        Response response = UserClient.createOrder(order, "");

        UserChecks.check400AndErrorMessageIdsMustBeProvided(response);
    }

    @Test
    public void createOrderWithInvalidIngredientsReturns500() {
        ArrayList<String> listOfInvalidIngredients = new ArrayList<>();
        listOfInvalidIngredients.add("invalidAbrakadabra");
        Order order = new Order(listOfInvalidIngredients);

        Response response = UserClient.createOrder(order, accessToken);

        UserChecks.check500(response);
    }

}