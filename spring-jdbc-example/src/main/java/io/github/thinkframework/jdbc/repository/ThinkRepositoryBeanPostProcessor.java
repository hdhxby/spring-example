package io.github.thinkframework.jdbc.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * 处理自定义注解
 * @author lixiaobin
 */
public class ThinkRepositoryBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(ThinkRepositoryBeanPostProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredFields()).forEach(field -> {
            if(field.getAnnotation(ThinkAutowiredRepository.class) != null){
                if(field.getType().isAssignableFrom(CrudRepository.class)) {
                    Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
//                    if (types.length == 2) {
//                            types[1].getClass();
                            try {
                                field.setAccessible(true);
                                field.set(bean, Proxy.newProxyInstance(ThinkRepositoryFactoryBean.class.getClassLoader(), new Class[]{CrudRepository.class}, (proxy, method, args) -> {
                                    ThinkCrudRepository repository = new ThinkCrudRepository();
                                    return ThinkCrudRepository.class.getMethod(method.getName()).invoke(repository, args);
                                }));
                            } catch (IllegalAccessException e) {
                                log.error("IllegalAccess", e);
                            }
//                    }
                }
            }
        });
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredFields()).forEach(field -> {
            if(field.getAnnotation(ThinkAutowiredRepository.class) != null){
                if(field.getType().isAssignableFrom(CrudRepository.class)) {
                    Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
//                    if (types.length == 2) {
//                            types[1].getClass();
                    try {
                        field.set(bean, Proxy.newProxyInstance(ThinkRepositoryFactoryBean.class.getClassLoader(), new Class[]{CrudRepository.class}, (proxy, method, args) -> {
                            ThinkCrudRepository repository = new ThinkCrudRepository();
                            return ThinkCrudRepository.class.getMethod(method.getName()).invoke(repository, args);
                        }));
                    } catch (IllegalAccessException e) {
                        log.error("IllegalAccess", e);
                    }
//                    }
                }
            }
        });
        return bean;
    }
}
