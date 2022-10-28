package io.github.thinkframework.jdbc.repository;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class ThinkRepositoryFactoryBean implements FactoryBean {

    private String beanClazzName;

    @Override
    public Object getObject() {
        return Proxy.newProxyInstance(ThinkRepositoryFactoryBean.class.getClassLoader(), new Class[]{getObjectType()}, (proxy, method, args) -> {
            ThinkCrudRepository repository = new ThinkCrudRepository();
            return ThinkCrudRepository.class.getMethod(method.getName()).invoke(repository,args);
        });
    }

    @Override
    public Class<?> getObjectType() {
        try {
            return Class.forName(beanClazzName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public String getBeanClazzName() {
        return beanClazzName;
    }

    public void setBeanClazzName(String beanClazzName) {
        this.beanClazzName = beanClazzName;
    }
}
