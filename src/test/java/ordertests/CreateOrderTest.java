package ordertests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends CommonOrderTest {

    @Test
    @DisplayName("Авторизованный пользователь создает заказ с ингредиентами")
    @Description("Проверка создания заказа с ингредиентами авторизованным пользователем")
    public void createOrderWithIngredientAuthUser(){
        userSteps.userData(user);
        userSteps.createUser(user);
        userSteps.loginUser(user);
        userSteps.getAccessToken(user);
        ingredientStep.setListIngredients(order);

        orderSteps
                .createOrderAuthUser(order, user)
                .statusCode(200)
                .body("success", is(true))
                .and()
                .body("name", notNullValue());
    }

    @Test
    @DisplayName("Не авторизованный пользователь создает заказ с ингредиентами")
    @Description("Проверка создания заказа с ингредиентами не авторизованного пользователя")
    public void createOrderWithIngredientWithoutNotAuthUser(){
        ingredientStep.setListIngredients(order);

        orderSteps
                .createOrderWithoutAuth(order)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }

    @Test
    @DisplayName("Авторизованный пользователь создает заказ без ингредиентов")
    @Description("Проверка создания заказа без ингредиентов авторизованным пользователем")
    public void createOrderWithoutIngredientAuthUser(){
        userSteps.userData(user);
        userSteps.createUser(user);
        userSteps.loginUser(user);
        userSteps.getAccessToken(user);
        ingredientStep.setEmptyListIngredient(order);

        orderSteps
                .createOrderAuthUser(order, user)
                .statusCode(400)
                .body("success", is(false))
                .and()
                .body("message", is("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Не авторизованный пользователь создает заказ без ингредиентов")
    @Description("Проверка создания заказа без ингредиентов не авторизованного пользователя")
    public void createOrderWithIngredientNotAuthUser(){
        ingredientStep.setEmptyListIngredient(order);

        orderSteps
                .createOrderWithoutAuth(order)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }

    @Test
    @DisplayName("Авторизованный пользователь создает заказ с невалидным хеш ингредиента")
    @Description("Проверка создания заказа с невалидным хеш ингредиента авторизованным пользователем")
    public void createOrderWithInvalidHashIngredientAuthUser(){
        userSteps.userData(user);
        userSteps.createUser(user);
        userSteps.loginUser(user);
        userSteps.getAccessToken(user);
        ingredientStep.setIncorrectListOfIngredient(order);

        orderSteps
                .createOrderAuthUser(order, user)
                .statusCode(500);
    }

    @Test
    @DisplayName("Не авторизованный пользователь создает заказ без ингредиентов")
    @Description("Проверка создания заказа без ингредиентов не авторизованного пользователя")
    public void createOrderWithInvalidHashIngredientNotAuthUser(){
        ingredientStep.setIncorrectListOfIngredient(order);

        orderSteps
                .createOrderWithoutAuth(order)
                .statusCode(401)
                .body("success", is(false))
                .and()
                .body("message", is("You should be authorised"));
    }
}
