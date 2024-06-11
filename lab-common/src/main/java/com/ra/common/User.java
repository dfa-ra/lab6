package com.ra.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String login;
    private String password;
    private String name;
    private String surname;
    private String eMail;

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }
}
