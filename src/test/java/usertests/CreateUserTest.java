package usertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;


public class CreateUserTest extends CommonUserTest {

    @Before
    public void setUpUser(){
        userSteps.userData(user);
    }

    @Test
    @DisplayName("Создание УЗ пользователя")
    @Description("Создание нового пользователя с корректными данными")
    public void createUserTrue(){
        userSteps
                .createUser(user)
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Создание УЗ уже зарегистрированного пользователя")
    @Description("Проверка ответа 403")
    public void createAlreadyCreatedUser(){
        userSteps.createUser(user);

        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false))
                .and()
                .body("message", is("User already exists"));
    }

    @Test
    @DisplayName("Создание УЗ без email")
    @Description("Проверка значения null")
    public void createUserWithNullEmail() {
        user.setEmail(null);

        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false))
                .and()
                .body("message", is("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание УЗ с пустой строкой email")
    @Description("Проверка пустой строки email")
    public void createUserWithEmptyEmail() {
        user.setEmail("");

        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false));
    }

    @Test
    @DisplayName("Создание УЗ без password")
    @Description("Проверка значения null")
    public void createUserWithNullPassword() {
        user.setPassword(null);

        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false))
                .and()
                .body("message", is("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание УЗ с пустой строкой password")
    @Description("Проверка пустой строки password")
    public void createUserWithEmptyPassword() {
        user.setEmail("");

        userSteps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false));
    }
}
