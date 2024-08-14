package models;

import lombok.Data;

@Data
public class UserModel {


    private String email;
    private String password;
    private String name;
    private String accessToken;

}
