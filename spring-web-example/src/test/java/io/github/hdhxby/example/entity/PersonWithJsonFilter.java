package io.github.hdhxby.example.entity;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("alias")
public class PersonWithJsonFilter extends Person<String,Integer>{

    private String alias;

    private transient String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
