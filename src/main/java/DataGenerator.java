import com.github.javafaker.Faker;
import io.restassured.response.Response;
import lombok.experimental.UtilityClass;
import requestPOJOs.Order;
import requestPOJOs.RegisterUser;

import java.util.Locale;
import java.util.Random;

@UtilityClass
public class DataGenerator {
    static Faker eng = new Faker(Locale.US);
    public RegisterUser getRandomRegisterUser() {
        var email = eng.internet().emailAddress();
        var password = eng.bothify("?????#####???###");
        var name = eng.name().firstName();

        return new RegisterUser(email, password, name);
    }

    public Order getRandomOrder() {
        Order order = new Order();
        Random random = new Random();
        Response response = UserClient.getIngredients();

        int ingredientsToAdd = random.nextInt(9) + 2;       // добавить в заказ от 2 до 10 ингредиентов
        int ingredientsTotal = response.jsonPath().getList("data").size();

        for (int i = 0; i < ingredientsToAdd; i++) {
            int j = random.nextInt(ingredientsTotal);              // выбрать случайный ингредиент из имеющихся
            String ingredientId = response.then()
                    .extract()
                    .path("data["+ j +"]._id");
            order.addIngredients(ingredientId);
        }
        System.out.println("*\n*\n*\nNumber of ingredients added: " + ingredientsToAdd + "\n*\n*\n*");
        return order;
    }

}
