import com.github.javafaker.Faker;
import requestPOJOs.RegisterUser;

import java.util.Locale;

public class DataGenerator {
    public static RegisterUser getRandomRegisterUser() {
        Faker eng = new Faker(Locale.US);

        var email = eng.internet().emailAddress();
        var password = eng.bothify("?????#####???###");
        var name = eng.name().firstName();

        return new RegisterUser(email, password, name);
    }
}
