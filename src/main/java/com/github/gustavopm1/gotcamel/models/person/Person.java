package com.github.gustavopm1.gotcamel.models.person;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Person {
    String name;
    Integer id;
    Date birthday;
    Date deathday;
    String place_of_birth;
    String profile_path;
    String biography;
}
