package javax.validation;

import org.hibernate.validator.internal.engine.DefaultClockProvider;
import org.hibernate.validator.internal.engine.DefaultParameterNameProvider;
import org.hibernate.validator.internal.engine.ValidatorContextImpl;
import org.hibernate.validator.internal.engine.ValidatorFactoryImpl;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;
import java.util.Set;

public class ValidatorStandardBeanExpressionResolverTest {

    public class Person {

        @NotNull
        public String name;
        @NotNull
        @Min(0)
        public Integer age;

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
    }
    // 用于Java Bean校验的校验器
    private Validator obtainValidator() {
        // 1、使用【默认配置】得到一个校验工厂  这个配置可以来自于provider、SPI提供
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        // 2、得到一个校验器
        return validatorFactory.getValidator();
    }

    // 用于方法校验的校验器
    private ExecutableValidator obtainExecutableValidator() {
        return obtainValidator().forExecutables();
    }

    @Test
    public void test1() {
        Validator validator = obtainValidator();

        Person person = new Person();
        person.setAge(-1);
        Set<ConstraintViolation<Person>> result = validator.validate(person);

        // 输出校验结果
        result.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue()).forEach(System.out::println);
    }

    @Test
    public void test2() {
        ValidatorFactoryImpl validatorFactory = (ValidatorFactoryImpl) Validation.buildDefaultValidatorFactory();
        // 使用默认的Context上下文，并且初始化一个Validator实例
        // 必须传入一个校验器工厂实例哦
        ValidatorContext validatorContext = new ValidatorContextImpl(validatorFactory)
                .parameterNameProvider(new DefaultParameterNameProvider())
                .clockProvider(DefaultClockProvider.INSTANCE);

        // 通过该上下文，生成校验器实例（注意：调用多次，生成实例是多个哟）
        System.out.println(validatorContext.getValidator());
    }

    @Test
    public void test3() {
        Validator validator = Validation.buildDefaultValidatorFactory().usingContext()
                .parameterNameProvider(new DefaultParameterNameProvider())
                .clockProvider(DefaultClockProvider.INSTANCE)
                .getValidator();
    }
}
