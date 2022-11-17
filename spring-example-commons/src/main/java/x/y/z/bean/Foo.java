package x.y.z.bean;

import javax.validation.constraints.NotNull;

public class Foo {

    @NotNull
    private String name;

    public Foo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
