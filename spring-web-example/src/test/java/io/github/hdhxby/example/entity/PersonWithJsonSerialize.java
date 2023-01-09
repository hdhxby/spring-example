package io.github.hdhxby.example.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = PersonStdSerializer.class)
public class PersonWithJsonSerialize extends Person<String,Integer>{

    private String alias;

    private transient String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
