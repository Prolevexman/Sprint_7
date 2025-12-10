package prolevexman.data;

import com.github.javafaker.Faker;
import prolevexman.model.request.Courier;

import java.util.Locale;

public class CourierGenerator {
    private static final Faker faker = new Faker(new Locale("en"));

    public static Courier randomCourierAllFields() {
        String firstName = faker.name().firstName();
        String login = faker.name().username();
        String password = faker.internet().password(8,12);

        return new Courier(firstName, login, password);
    }

    public static Courier courierWithoutLogin() {
        String firstName = faker.name().firstName();
        String password = faker.internet().password(8,12);

        return new Courier(firstName, null, password);
    }

    public static Courier courierWithoutPassword() {
        String firstName = faker.name().firstName();
        String login = faker.name().username();

        return new Courier(firstName, login, null);
    }

    public static Courier courierWithoutFirstName() {
        String login = faker.name().username();
        String password = faker.internet().password(8,12);

        return new Courier(null, login, password);
    }
}
