import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import requestPOJOs.Order;
import requestPOJOs.RegisterUser;
import responsePOJOs.Ingredient;
import responsePOJOs.OrderResponseValidAuth;

import java.util.List;

// Тесты с корректной авторизацией
@Tag("createOrder")
public class CreateOrderDeeperTest {
    private static Order order;
    private static String accessToken;
    private static List<Ingredient> ingredients;
    @BeforeEach
    public void setUp() {
        RegisterUser user = DataGenerator.getRandomRegisterUser();

        Response response = UserClient.registerUser(user);

        accessToken = UserClient.getAccessTokenWithoutBearer(response);
    }

    @AfterEach
    public void tearDown() {
        Allure.parameter("order", order);
        Allure.parameter("accessToken", accessToken);

        UserClient.deleteUser(accessToken);
    }

    @Test
    public void ingredientsImageIsPngTest() {
        order = DataGenerator.getRandomOrder();

        Response response = UserClient.createOrder(order, accessToken);

        OrderResponseValidAuth orderResponseValidAuth = response.as(OrderResponseValidAuth.class);

        ingredients = orderResponseValidAuth.getOrder().getIngredients();

        boolean allImagesArePng = ingredients.stream()
                .map(ingredient -> ingredient.image)
                .allMatch(image -> image != null && image.endsWith(".png"));

        Assertions.assertTrue(allImagesArePng);

    }
    @Test
    public void ownerEmailHasAtTest() {
        order = DataGenerator.getRandomOrder();

        Response response = UserClient.createOrder(order, accessToken);

        OrderResponseValidAuth orderResponseValidAuth = response.as(OrderResponseValidAuth.class);

        String ownersEmail = orderResponseValidAuth.getOrder().getOwner().email;

        System.out.println(ownersEmail);
        Assertions.assertTrue(ownersEmail.contains("@"));
    }

}
