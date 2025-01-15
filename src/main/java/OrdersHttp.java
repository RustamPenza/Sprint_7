import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class OrdersHttp extends BaseHttpClient{

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        Response response = doPostRequest(URL.ORDERS, order);
        Integer track = response.getBody().jsonPath().get("track");
        if (track != null) {
            order.setTrack(track);
        }
        return response;
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrders() {
        return doGetRequest(URL.ORDERS);
    }
}
