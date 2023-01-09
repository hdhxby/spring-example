package io.github.hdhxby.example.entity;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import io.github.hdhxby.example.entity.Person;
import io.github.hdhxby.example.entity.Pet;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersonCodec extends ObjectCodec {

    @Override
    public Version version() {
        return null;
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, Class<T> aClass) throws IOException {

        Person person = new Person();

        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            String fieldname = jsonParser.getCurrentName();
            if ("name".equals(fieldname)) {
                person.setName(jsonParser.nextTextValue());
            } else if ("age".equals(fieldname)) {
                person.setAge(jsonParser.nextIntValue(0));
            } else if ("dog".equals(fieldname)) {
                jsonParser.nextToken();
                // 构造一个dog实例（同样的，实际场景是利用反射构造的哦）
                Pet pet = new Pet();
                person.setPet(pet);

                while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                    String dogFieldName = jsonParser.getCurrentName();
                    if ("name".equals(dogFieldName)) {
                        pet.setName(jsonParser.nextTextValue());
                    } else if ("color".equals(dogFieldName)) {
                        pet.setColor(Color.getColor(jsonParser.nextTextValue()));
                    }
                }
            } else if ("hobbies".equals(fieldname)) {
                jsonParser.nextToken();

                List<String> hobbies = new ArrayList<>();
                person.setHobbies(hobbies);
                while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                    hobbies.add(jsonParser.getText());
                }
            }
        }
        return (T) person;
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, TypeReference<T> typeReference) throws IOException {
        return null;
    }

    @Override
    public <T> T readValue(JsonParser jsonParser, ResolvedType resolvedType) throws IOException {
        return null;
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, Class<T> aClass) throws IOException {
        return null;
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, TypeReference<T> typeReference) throws IOException {
        return null;
    }

    @Override
    public <T> Iterator<T> readValues(JsonParser jsonParser, ResolvedType resolvedType) throws IOException {
        return null;
    }

    @Override
    public void writeValue(JsonGenerator jsonGenerator, Object o) throws IOException {

    }

    @Override
    public <T extends TreeNode> T readTree(JsonParser jsonParser) throws IOException {
        return null;
    }

    @Override
    public void writeTree(JsonGenerator jsonGenerator, TreeNode treeNode) throws IOException {

    }

    @Override
    public TreeNode createObjectNode() {
        return null;
    }

    @Override
    public TreeNode createArrayNode() {
        return null;
    }

    @Override
    public JsonParser treeAsTokens(TreeNode treeNode) {
        return null;
    }

    @Override
    public <T> T treeToValue(TreeNode treeNode, Class<T> aClass) throws JsonProcessingException {
        return null;
    }
}
