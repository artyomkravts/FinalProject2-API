import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import requestPOJOs.Order;
import requestPOJOs.RegisterUser;

public class GetOrdersTest {
    private static RegisterUser user;
    private static String accessToken;
    private static Order order;
    @BeforeEach
    public void setUp() {
        user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.registerUser(user);

        accessToken = UserClient.getAccessTokenWithoutBearer(response);

        order = DataGenerator.getRandomOrder();

        UserClient.createOrder(order, accessToken);
    }

    @AfterEach
    public void tearDown() {
        Allure.parameter("user", user);
        Allure.parameter("order", order);
        Allure.parameter("accessToken", accessToken);

        UserClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Get order with valid auth successful")
    @Description("Positive test checks 200 and that response returns list of orders")
    public void getOrdersValidAuthReturnsOkAndListOfOrders() {
        Response response = UserClient.getOrder(accessToken);

        UserChecks.check200AndListOfOrders(response);
    }

    @Test
    @DisplayName("Get order without auth failed")
    @Description("Negative test checks 401 and that response returns error message - success: false")
    public void getOrdersNoAuthReturns401AndSuccessFalse() {
        accessToken = "";
        Response response = UserClient.getOrder(accessToken);

        UserChecks.check401AndSuccessFalse(response);
    }
}
