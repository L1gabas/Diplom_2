package staps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import models.OrderModel;
import models.UserModel;

import static io.restassured.RestAssured.given;
import static urls.ApiUrls.*;

public class OrderSteps {

    @Step("Создать заказ авторизованным пользователем")
    public ValidatableResponse createOrderAuthUser(OrderModel order, UserModel user) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(TEST_STAND)
                .body(order)
                .when()
                .post(ORDER_DATA)
                .then();
    }

    @Step("Создать заказ пользователем без авторизации")
    public ValidatableResponse createOrderWithoutAuth(OrderModel order) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(TEST_STAND)
                .body(order)
                .when()
                .post(ORDER_DATA)
                .then();
    }

    @Step("Получить заказ авторизованным пользователем")
    public ValidatableResponse authUserGetOrder(UserModel user) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(TEST_STAND)
                .when()
                .get(ORDER_DATA)
                .then();
    }

    @Step("Получить заказ пользователем без авторизации")
    public ValidatableResponse withoutAuthGetOrder() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(TEST_STAND)
                .when()
                .get(ORDER_DATA)
                .then();
    }
}
