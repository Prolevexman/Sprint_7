package prolevexman.data;

import com.github.javafaker.Faker;
import prolevexman.model.request.Order;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OrderGenerator {
    private static final Faker faker = new Faker(new Locale("en"));
    private static final Random random = new Random();

    public static Order randomOrderWithColors(List<String> colors) {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().fullAddress();
        String metroStation = faker.address().streetName();
        String phone = faker.phoneNumber().cellPhone();
        int rentTime = random.nextInt(10) + 1;
        String deliveryDate = faker.date().future(10, TimeUnit.DAYS).toInstant().toString().substring(0,10);
        String comment = faker.lorem().sentence();
        return new Order(address, colors, comment, deliveryDate, firstName, lastName, metroStation, phone, rentTime);
    }

    public  static Order randomOrderWithoutColors() {
        return randomOrderWithColors(Collections.emptyList());
    }

    public  static Order randomOrderWithOneColor(String color) {
        return randomOrderWithColors(Collections.singletonList(color));
    }

}
