import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;


public class OrdersTest {

    private OrdersHttp ordersHttp = new OrdersHttp();

    @Test
    @DisplayName("Получить весь список заказов")
    @Description("Проверяем, что возвращается список заказов")
    public void getOrdersList() {
        ordersHttp.getOrders()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
