##### 注意

```

1.项目中@Transactional采用spring AOP动态代理模式，默认cglib代理。
2.TransactionManager, TransactionInterceptor使用spring auto-configuration自动生成
3.RedisTemplate，StringRedisTemplate使用spring auto-configuration自动生成
4.@Aspect 采用Aspectj compiler编译时织入即静态代理模式

```
