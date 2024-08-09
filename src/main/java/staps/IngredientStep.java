package staps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import models.OrderModel;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static urls.ApiUrls.*;

public class IngredientStep {

    @Step("Получить список ингредиентов")
    public ValidatableResponse getIngredientList(){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(TEST_STAND)
                .when()
                .get(GET_INGREDIENT)
                .then();
    }

    @Step("Добавить в заказ массив")
    public void setListIngredients(OrderModel order){
        List<String> ingredientId = getIngredientList().extract()
                .body().path("data._id");

        List<String> ingredientsList = new ArrayList<>();

        String firstIngredient = ingredientId.get(new Random().nextInt(ingredientId.size()));
        String secondIngredient = ingredientId.get(new Random().nextInt(ingredientId.size()));

        ingredientsList.add(firstIngredient);
        ingredientsList.add(secondIngredient);

        order.setIngredients(ingredientsList);
    }

    @Step("Добавить пустой массив ингредиентов")
    public void setEmptyListIngredient(OrderModel order){
        List<String> ingredientsList = new ArrayList<>();

        order.setIngredients(ingredientsList);
    }

    @Step("Добавить в заказ массив с некорректными ингредиентами")
    public void setIncorrectListOfIngredient(OrderModel order){
        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add(RandomStringUtils.randomAlphabetic(5));
        ingredientsList.add(RandomStringUtils.randomAlphabetic(6));
        ingredientsList.add(RandomStringUtils.randomAlphabetic(7));

        order.setIngredients(ingredientsList);
    }
}

