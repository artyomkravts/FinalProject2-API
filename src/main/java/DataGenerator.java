import com.github.javafaker.Faker;
import io.restassured.response.Response;
import requestPOJOs.Order;
import requestPOJOs.RegisterUser;

import java.util.Locale;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    static Faker eng = new Faker(Locale.US);
    public static RegisterUser getRandomRegisterUser() {
        var email = eng.internet().emailAddress();
        var password = eng.bothify("?????#####???###");
        var name = eng.name().firstName();

        return new RegisterUser(email, password, name);
    }

    public static Order getRandomOrder() {
        Order order = new Order();
        Random random = new Random();
        Response response = UserClient.getIngredients();

        int ingredientCount = response.jsonPath().getList("data").size();

        for (int i = 0; i < random.nextInt(9) + 2; i++) {  // добавить в заказ от 2 до 10 ингредиентов
            int j = random.nextInt(ingredientCount);             // выбрать случайный ингредиент из имеющихся
            String ingredientId = response.then().log().all()
                    .extract()
                    .path("data["+ j +"]._id");
            order.addIngredients(ingredientId);
        }
        return order;
    }

}
