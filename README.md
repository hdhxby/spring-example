# spring-context-support
> spring-context-supports包含定时任务,邮件,缓存,FreeMarker相关的功能,不需要关注

依赖关系
```
spring-context-supports
    spring-context
        spring-aop
            spring-beans
                spring-core
        spring-expression
                spring-core

```

# spring-aspects
> spring-aspects包含了一些自定义Aspect,也不需要关注

依赖关系
```
spring-aspects
```

# spring-core
> 不通过类加载器获取Class元数据的方式

提供了
类元数据加载的功能
    MetadataReaderFactory
        MetadataReader
            ClassMetadata
            AnnotationMetadata
- 环境变量
    - StandardEnvironment(MutablePropertySources),
- 类型转换(字符串转对象,对象转字符串等功能),
    - Converter
    - ConversionService
- 资源加载
    Resource
    ResourceLoader,
    ResourceEditor(把字符串转换成Resource)

**获取元数据的方式,没有批量获取元数据的方式**

---
# spring-bean
> 将spring-core获取到的元数据生成BeanDefinition

BeanFactory
> 注册BeanDefinition获取Bean


AutowireCandidateResolver : 

BeanPostProcessor
InstantiationAwareBeanPostProcessor
SmartInstantiationAwareBeanPostProcessor


StandardBeanExpressionResolver: #{}的解析


- BeanFactoryPostProcessor: 修改BeanDefinition
    - 留给spring-context的扩展,PropertySourcesPlaceholderConfigurer实现此接口完成了${}解析
- BeanDefinitionRegistryPostProcessor : 注册BeanDefinition,继承BeanFactoryPostProcessor
    - 留给spring-context的扩展,ConfigurationClassPostProcessor实现此接口完成了BeanDefinition的注册
  

并没有提供任何搜集bean定义的功能
扫描Bean定义,注册Bean定义的实现都在context里

spring-aop
融入spring-bean生命周期中的基于后置处理器的增强

- InfrastructureAdvisorAutoProxyCreator : @EnableTransactionManagement会注册这个, 跳过基础设施@Role(BeanDefinition.ROLE_INFRASTRUCTURE),其他的如果需要就创建代理
- ~~AspectJAwareAdvisorAutoProxyCreator~~ : AopNamespaceHandler会注册这个, 如果需要就创建代理
- AnnotationAwareAspectJAutoProxyCreator : @EnableAspectJAutoProxy会注册这个, 如果需要就创建代理


spring-context
完成对spring-bean的组装
国际化
事件

- conditionEvaluator : 条件装配

jdbc
```
spring-jdbc
    spring-tx
        spring-beans
            spring-core
```
spring-tx
事务的支持

spring-jdbc
JdbcTemplate

# spring-web
> 
```
spring-web
    spring-beans
        spring-core
```

# spring-webmvc
>
```
spring-webmvc
    spring-web
        spring-beans
            spring-core
    spring-context
        spring-aop
            spring-beans
                spring-core
        spring-expression
            spring-core
```

# spring-r2dbc
> 没普及

# spring-webflux
> 不常用
```
spring-webflux
    spring-web
        spring-beans
            spring-core
```

```
spring-websocket
    spring-web
        spring-beans
            spring-core
    spring-context
        spring-aop
            spring-beans
                spring-core
        spring-expression
            spring-core
```

# spring-orm
> hibernate,用不到

# spring-oxm
> 没见过

# spring-jms
> activemq,过时

# spring-test
> 测试相关
```
spring-test
    spring-core
```