package io.github.hdhxby.example.entity;

import java.util.List;

public class Person<S,T> {
    private String name;


    private Integer age;

    private List<String> hobbies;
    private Pet pet;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Pet setPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
