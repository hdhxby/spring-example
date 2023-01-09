package io.github.hdhxby.example.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import io.github.hdhxby.example.entity.Person;

import java.io.IOException;

public class PersonStdSerializer extends StdSerializer<Person> {
    // 提供空构造是为了让可以使用注解方式，一般建议提供
    public PersonStdSerializer() {
        this(Person.class);
    }

    public PersonStdSerializer(Class<Person> t) {
        super(t);
    }

    @Override
    public void serialize(Person value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("jsonStr", value.toString());
        gen.writeEndObject();
    }
}
