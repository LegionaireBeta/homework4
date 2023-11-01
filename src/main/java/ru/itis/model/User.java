package ru.itis.model;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Builder

public class User {

    private long id;
    private UUID uuidOfUser;
    private String nameOfUser;
    private String surnameOfUser;
    private String usernameOfUser;
    private Integer ageOfUser;
    private String passwordOfUser;

}
