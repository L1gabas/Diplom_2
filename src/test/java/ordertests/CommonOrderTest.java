package ordertests;

import models.OrderModel;
import models.UserModel;
import org.junit.After;
import org.junit.Before;
import steps.IngredientStep;
import steps.OrderSteps;
import steps.UserSteps;

public class CommonOrderTest {

    protected static OrderModel order;
    protected static OrderSteps orderSteps;
    protected static UserSteps userSteps;
    protected static UserModel user;
    protected static IngredientStep ingredientStep;

    @Before
    public void setUp(){
        order = new OrderModel();
        orderSteps = new OrderSteps();
        user = new UserModel();
        userSteps = new UserSteps();
        ingredientStep = new IngredientStep();
    }

    @After
    public void tearDown(){
        String userToken = user.getAccessToken();
        if (userToken != null){
            userSteps.deleteUser(user);
        } else {
            System.out.println("Пользователь не найден");
        }
    }
}
