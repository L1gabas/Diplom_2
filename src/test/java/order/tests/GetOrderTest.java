package order.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTest extends CommonOrderTest {

    @Test
    @DisplayName("Авторизованный пользователь получает заказ")
    @Description("Проверка получение заказа авторизованным пользователем")
    public void authUserGetOrder(){
        userSteps.userData(user);
        userSteps.createUser(user);
        userSteps.loginUser(user);
        userSteps.getAccessToken(user);

        orderSteps
                .authUserGetOrder(user)
                .statusCode(200)
                .body("success", is(true))
                .and()
                .body("total", notNullValue());
    }

    @Test
    @DisplayName("Не авторизованный пользователь получает заказ")
    @Description("Проверка получение заказа не авторизованным пользователем")
    public void notAuthUserGetOrder(){

        orderSteps
                .withoutAuthGetOrder()
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }
}
