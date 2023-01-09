package x.y.z.bean;

import javax.validation.constraints.NotNull;

public class Bar {

    @NotNull
    private String name;

    public Bar() {
    }

    public Bar(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
