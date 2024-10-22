import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requestPOJOs.Order;
import requestPOJOs.RegisterUser;

public class GetOrdersTest {
    private static RegisterUser user;
    private static String accessToken;
    private static Order order;
    @Before
    public void setUp() {
        user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.registerUser(user);

        accessToken = UserClient.getAccessTokenWithoutBearer(response);

        order = DataGenerator.getRandomOrder();

        UserClient.createOrder(order, accessToken);
    }

    @After
    public void tearDown() {
        UserClient.deleteUser(accessToken);
    }

    @Test
    public void getOrdersValidAuthReturnsOkAndListOfOrders() {
        Response response = UserClient.getOrder(accessToken);

        UserChecks.check200AndListOfOrders(response);
    }

    @Test
    public void getOrdersNoAuthReturns401AndSuccessFalse() {
        Response response = UserClient.getOrder("");

        UserChecks.check401AndSuccessFalse(response);
    }
}
