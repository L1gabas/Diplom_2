package UserTests;

import models.UserModel;
import org.junit.After;
import org.junit.Before;
import staps.UserSteps;

public class CommonUserTest {

    protected static UserSteps userSteps;
    protected static UserModel user;

    @Before
    public void setUp(){
        user = new UserModel();
        userSteps = new UserSteps();
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