import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class LoginCourierTest {

    private Courier courier;
    private CourierHttp courierHttp = new CourierHttp();

    @Before
    public void setUp() {
        courier = new Courier("azamat", "223344");
        courierHttp.createCourier(courier);
        courierHttp.loginCourier(courier);
    }

    @After
    public void endTest() {
        if (!courier.getLogin().equals("azamat") || !courier.getPassword().equals("223344")) {
            courier.setLogin("azamat");
            courier.setPassword("223344");
        }
        courierHttp.deleteCourier(courier);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Ответ возвращается с кодом 200 и содержит id != null")
    public void loginCourier() {
        Response response = courierHttp.loginCourier(courier);
        assertEquals(200, response.statusCode());
        assertNotNull(response.getBody().jsonPath().get("id"));
    }

    @Test
    @DisplayName("Авторизация без атрибута login")
    @Description("Ответ возвращается с кодом 400 и текстом 'Недостаточно данных для входа'")
    public void loginCourierWithoutLogin() {
        courier.setLogin("");
        Response response = courierHttp.loginCourier(courier);
        assertEquals(400, response.statusCode());
        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = response.getBody().jsonPath().get("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Авторизация без атрибута password")
    @Description("Ответ возвращается с кодом 400 и текстом \"Недостаточно данных для входа\"")
    public void loginCourierWithoutPassword() {
        courier.setPassword("");
        Response response = courierHttp.loginCourier(courier);
        assertEquals(400, response.statusCode());
        String expectedMessage = "Недостаточно данных для входа";
        String actualMessage = response.getBody().jsonPath().get("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Авторизация с несуществующим логином")
    @Description("Ответ возвращается с кодом 404 и текстом \"Учетная запись не найдена\"")
    public void loginCourierWithFakeLogin() {
        courier.setLogin("azaza");
        Response response = courierHttp.loginCourier(courier);
        assertEquals(404, response.statusCode());
        String expectedMessage = "Учетная запись не найдена";
        String actualMessage = response.getBody().jsonPath().get("message");
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Ответ возвращается с кодом 404 и текстом \"Учетная запись не найдена\"")
    public void loginCourierWithFakePassword() {
        courier.setPassword("111111");
        Response response = courierHttp.loginCourier(courier);
        assertEquals(404, response.statusCode());
        String expectedMessage = "Учетная запись не найдена";
        String actualMessage = response.getBody().jsonPath().get("message");
        assertEquals(expectedMessage, actualMessage);
    }


}
