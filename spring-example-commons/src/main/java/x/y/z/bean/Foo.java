package x.y.z.bean;

import org.springframework.lang.UsesJava8;

import javax.validation.constraints.NotNull;

@x.y.z.annotation.Foo
public class Foo {

    @NotNull
    private String name;

    private Bar bar;

    public Foo() {
    }

    public Foo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bar getBar() {
        return bar;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }
}
