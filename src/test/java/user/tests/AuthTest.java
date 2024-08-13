package user.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class AuthTest extends CommonUserTest {

    @Before
    public void setUpUser(){
        userSteps.userData(user);
        userSteps.createUser(user);
    }

    @Test
    @DisplayName("Пользователь авторизован")
    @Description("Проверка успешной авторизации пользователя")
    public void successAuthUser(){
        userSteps
                .loginUser(user)
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Пользователь с некорректным email")
    @Description("Проверка авторизации с некорректным email")
    public void incorrectEmailAuth(){
        user.setEmail((RandomStringUtils.randomAlphabetic(20)
                .toLowerCase()) + "@yandex.ru");

        userSteps
                .loginUser(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("email or password are incorrect"));
    }

    @Test
    @DisplayName("Пользователь с некорректным password")
    @Description("Проверка авторизации с некорректным password")
    public void incorrectPasswordAuth(){
        user.setPassword(RandomStringUtils.randomAlphabetic(18));

        userSteps
                .loginUser(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("email or password are incorrect"));
    }
}
