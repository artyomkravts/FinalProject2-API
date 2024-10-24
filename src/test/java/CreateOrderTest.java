import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requestPOJOs.Order;
import requestPOJOs.RegisterUser;

import java.util.ArrayList;

public class CreateOrderTest {
    private static RegisterUser user;
    private static Order order;
    private static String accessToken;
    @Before
    public void setUp() {
            user = DataGenerator.getRandomRegisterUser();

            Response response = UserClient.registerUser(user);

            accessToken = UserClient.getAccessTokenWithoutBearer(response);
        }

    @After
    public void tearDown() {
        Allure.parameter("order", order);
        Allure.parameter("accessToken", accessToken);

        UserClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Create order with auth successful")
    @Description("Positive test checks 200 and that response returns user credentials from the user's registration")
    public void createOrderWithValidAuthTokenReturnsOkAndUserCreds() {
        order = DataGenerator.getRandomOrder();

        Response response = UserClient.createOrder(order, accessToken);

        UserChecks.check200AndUserCreds(response, user);
    }

    @Test
    @DisplayName("Create order without auth successful")
    @Description("Positive test checks 200 and that response does NOT return user credentials")
    public void createOrderWithoutAuthReturnsOkAndNoUserCreds() {
        accessToken = "";
        order = DataGenerator.getRandomOrder();

        Response response = UserClient.createOrder(order, accessToken);

        UserChecks.check200SuccessTrueAndNoUserCreds(response);
    }

    @Test
    @DisplayName("Create order without ingredients failed")
    @Description("Negative test checks 400 and that response returns error message: ids must be provided")
    public void createOrderWithoutIngredientsReturns400AndErrorMessage() {
        order = new Order();

        Response response = UserClient.createOrder(order, accessToken);

        UserChecks.check400AndErrorMessageIdsMustBeProvided(response);
    }

    @Test
    @DisplayName("Create order without auth && without ingredients failed")
    @Description("Negative test checks 400 and that response returns error message: ids must be provided")
    public void createOrderWithoutAuthWithoutIngredientsReturns400AndErrorMessage() {
        accessToken = "";
        order = new Order();

        Response response = UserClient.createOrder(order, "");

        UserChecks.check400AndErrorMessageIdsMustBeProvided(response);
    }

    @Test
    @DisplayName("Create order with invalid ingredients failed")
    @Description("Negative test uses broken ingredient hashes and checks 500")
    public void createOrderWithInvalidIngredientsReturns500() {
        ArrayList<String> listOfInvalidIngredients = new ArrayList<>();
        listOfInvalidIngredients.add("invalidAbrakadabra");
        order = new Order(listOfInvalidIngredients);

        Response response = UserClient.createOrder(order, accessToken);

        UserChecks.check500(response);
    }

}