package staps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import models.UserModel;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;
import static urls.ApiUrls.*;

public class UserSteps {

    @Step("Данные пользователя")
    public void userData(UserModel user) {
        user.setEmail((RandomStringUtils.randomAlphabetic(8)
                .toLowerCase()) + "@yandex.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(14));
        user.setName(RandomStringUtils.randomAlphabetic(9));
    }

    @Step("Извлечение токена авторизации")
    public static void getAccessToken(UserModel user) {
        String accessToken = loginUser(user).extract()
                .body().path("accessToken");
        user.setAccessToken(accessToken);
    }

    @Step("Создать нового пользователя")
    public ValidatableResponse createUser(UserModel user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(TEST_STAND)
                .body(user)
                .when()
                .post(CREATE_USER)
                .then();
    }

    @Step("Авторизация пользователя")
    public static ValidatableResponse loginUser(UserModel user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(TEST_STAND)
                .body(user)
                .when()
                .post(LOGIN_USER)
                .then();
    }

    @Step("Изменить данные авторизованного пользователя")
    public static ValidatableResponse changeAuthUserData(UserModel user) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(TEST_STAND)
                .body(user)
                .when()
                .patch(USER_DATA)
                .then();
    }

    @Step("Изменить данные не авторизованного пользователя")
    public static ValidatableResponse changeWithoutAuthUserData(UserModel user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(TEST_STAND)
                .body(user)
                .when()
                .patch(USER_DATA)
                .then();
    }

    @Step("Удалить пользователя")
    public void deleteUser(UserModel user) {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", user.getAccessToken())
                .baseUri(TEST_STAND)
                .when()
                .delete(USER_DATA)
                .then();
    }
}
