package usertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class EditUserTest extends CommonUserTest {
    private String newEmail;
    private String newPassword;
    private String newName;

    @Before
    public void setUpUser(){
        userSteps.userData(user);
        userSteps.createUser(user);
        userSteps.loginUser(user);
        userSteps.getAccessToken(user);

        newEmail = RandomStringUtils.randomAlphabetic(20)
                .toLowerCase() + "@yandex.ru";
        newPassword = RandomStringUtils.randomAlphabetic(18);
        newName = RandomStringUtils.randomAlphabetic(10);
    }

    @Test
    @DisplayName("Изменить email пользователя")
    @Description("Проверка изменения email созданного пользователя")
    public void changeUserEmail(){
        user.setEmail(newEmail);

        userSteps
                .changeAuthUserData(user)
                .statusCode(200)
                .body("success", is(true))
                .and()
                .body("user.email", is(user.getEmail()));
    }

    @Test
    @DisplayName("Изменить password пользователя")
    @Description("Проверка изменения пароля созданного пользователя")
    public void changeUserPassword() {
        user.setPassword(newPassword);

        userSteps
                .changeAuthUserData(user)
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Изменить name пользователя")
    @Description("Проверка изменения имени созданного пользователя")
    public void changeUserName() {
        user.setName(newName);

        userSteps
                .changeAuthUserData(user)
                .statusCode(200)
                .body("success", is(true))
                .and()
                .body("user.name", is(user.getName()));
    }

    @Test
    @DisplayName("Изменить email неавторизованного пользователя")
    @Description("Проверка изменения адреса почты без авторизации")
    public void changeUserEmailWithoutAuth(){
        user.setEmail(newEmail);

        userSteps
                .changeWithoutAuthUserData(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }

    @Test
    @DisplayName("Изменить password неавторизованного пользователя")
    @Description("Проверка изменения пароля без авторизации")
    public void changeUserPasswordWithoutAuth(){
        user.setPassword(newPassword);

        userSteps
                .changeWithoutAuthUserData(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }

    @Test
    @DisplayName("Изменить name неавторизованного пользователя")
    @Description("Проверка изменения имени пользователя без авторизации")
    public void changeUserNameWithoutAuth(){
        user.setName(newName);

        userSteps
                .changeWithoutAuthUserData(user)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }
}
