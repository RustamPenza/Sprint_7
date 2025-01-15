import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class ColorOrderTest {
    private Order order;
    private OrdersHttp ordersHttp = new OrdersHttp();

    private final String[] color;

    public ColorOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] result() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @Test
    @DisplayName("Создание заказа с разными вариантами цветов")
    @Description("Ответ возвращается с кодом 201 и содержит track != null")
    public void createOrderWithDifferentColor(){
        order = new Order("Naruto"
                , "Uchiha"
                , "Konoha, 142 apt."
                , 4
                , "+7 800 355 35 35"
                , 2
                , "2020-06-06"
                , "Saske, come back to Konoha"
                , color);
        Response response = ordersHttp.createOrder(order);
        assertEquals(201, response.statusCode());
        assertNotNull(response.getBody().jsonPath().get("track"));
    }
}
