# Demo of bug (?) in Spring Data JDBC

## Setup

1. create a PostgreSQL server, containing a table such as:

    ```sql
    create table if not exists myentity
    (
        id      bigserial primary key,
        values	double precision[]
    );
    ```

    For your convenience you can use the included Docker setup, which setups a PostgreSQL server and creates the correct schema:

    ```bash
    docker-compose up
    ```

2. configure the database in `DemoApplication` (should already be correct when using the Docker setup)
3. start the included Spring Application:

    ```bash
    ./mvnw package
    java -jar target/demo-0.0.1-SNAPSHOT.jar
    ```
4. the (CLI) application tries to insert a `double[]` in the database and fails with the following stack trace:
 
   ```
   .   ____          _            __ _ _
   /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
   ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
   \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
   '  |____| .__|_| |_|_| |_\__, | / / / /
   =========|_|==============|___/=/_/_/_/
   :: Spring Boot ::                (v2.5.4)

   2021-08-24 08:56:39.778  INFO 175079 --- [           main] com.example.demo.DemoApplication         : Starting DemoApplication v0.0.1-SNAPSHOT using Java 11.0.12 on with PID 175079 (spring-data-jdbc-double-array/target/demo-0.0.1-SNAPSHOT.jar started by xxx in spring-data-jdbc-double-array)
   2021-08-24 08:56:39.782  INFO 175079 --- [           main] com.example.demo.DemoApplication         : No active profile set, falling back to default profiles: default
   2021-08-24 08:56:40.409  INFO 175079 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JDBC repositories in DEFAULT mode.
   2021-08-24 08:56:40.465  INFO 175079 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 52 ms. Found 1 JDBC repository interfaces.
   2021-08-24 08:56:41.285  INFO 175079 --- [           main] com.example.demo.DemoApplication         : Started DemoApplication in 2.256 seconds (JVM running for 2.85)
   2021-08-24 08:56:41.568  INFO 175079 --- [           main] ConditionEvaluationReportLoggingListener :

   Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
   2021-08-24 08:56:41.599 ERROR 175079 --- [           main] o.s.boot.SpringApplication               : Application run failed

   java.lang.IllegalStateException: Failed to execute CommandLineRunner
   at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:794) ~[spring-boot-2.5.4.jar!/:2.5.4]
   at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:775) ~[spring-boot-2.5.4.jar!/:2.5.4]
   at org.springframework.boot.SpringApplication.run(SpringApplication.java:345) ~[spring-boot-2.5.4.jar!/:2.5.4]
   at org.springframework.boot.SpringApplication.run(SpringApplication.java:1343) ~[spring-boot-2.5.4.jar!/:2.5.4]
   at org.springframework.boot.SpringApplication.run(SpringApplication.java:1332) ~[spring-boot-2.5.4.jar!/:2.5.4]
   at com.example.demo.DemoApplication.main(DemoApplication.java:17) ~[classes!/:0.0.1-SNAPSHOT]
   at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
   at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
   at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
   at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
   at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:49) ~[demo-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
   at org.springframework.boot.loader.Launcher.launch(Launcher.java:108) ~[demo-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
   at org.springframework.boot.loader.Launcher.launch(Launcher.java:58) ~[demo-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
   at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:88) ~[demo-0.0.1-SNAPSHOT.jar:0.0.1-SNAPSHOT]
   Caused by: org.springframework.data.relational.core.conversion.DbActionExecutionException: Failed to execute DbAction.UpdateRoot(entity=com.example.demo.MyEntity@2ce86164)
   at org.springframework.data.jdbc.core.AggregateChangeExecutor.execute(AggregateChangeExecutor.java:89) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.AggregateChangeExecutor.lambda$execute$0(AggregateChangeExecutor.java:50) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at java.base/java.util.ArrayList.forEach(ArrayList.java:1541) ~[na:na]
   at org.springframework.data.relational.core.conversion.DefaultAggregateChange.forEachAction(DefaultAggregateChange.java:116) ~[spring-data-relational-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.AggregateChangeExecutor.execute(AggregateChangeExecutor.java:50) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.JdbcAggregateTemplate.store(JdbcAggregateTemplate.java:339) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.JdbcAggregateTemplate.save(JdbcAggregateTemplate.java:150) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.repository.support.SimpleJdbcRepository.save(SimpleJdbcRepository.java:60) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
   at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
   at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
   at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
   at org.springframework.data.repository.core.support.RepositoryMethodInvoker$RepositoryFragmentMethodInvoker.lambda$new$0(RepositoryMethodInvoker.java:289) ~[spring-data-commons-2.5.4.jar!/:2.5.4]
   at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:137) ~[spring-data-commons-2.5.4.jar!/:2.5.4]
   at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:121) ~[spring-data-commons-2.5.4.jar!/:2.5.4]
   at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:529) ~[spring-data-commons-2.5.4.jar!/:2.5.4]
   at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:285) ~[spring-data-commons-2.5.4.jar!/:2.5.4]
   at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:599) ~[spring-data-commons-2.5.4.jar!/:2.5.4]
   at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:163) ~[spring-data-commons-2.5.4.jar!/:2.5.4]
   at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:138) ~[spring-data-commons-2.5.4.jar!/:2.5.4]
   at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123) ~[spring-tx-5.3.9.jar!/:5.3.9]
   at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:388) ~[spring-tx-5.3.9.jar!/:5.3.9]
   at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-5.3.9.jar!/:5.3.9]
   at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:137) ~[spring-tx-5.3.9.jar!/:5.3.9]
   at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at com.sun.proxy.$Proxy51.save(Unknown Source) ~[na:na]
   at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
   at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
   at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
   at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
   at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:198) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:137) ~[spring-tx-5.3.9.jar!/:5.3.9]
   at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215) ~[spring-aop-5.3.9.jar!/:5.3.9]
   at com.sun.proxy.$Proxy51.save(Unknown Source) ~[na:na]
   at com.example.demo.DemoApplication.run(DemoApplication.java:41) ~[classes!/:0.0.1-SNAPSHOT]
   at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:791) ~[spring-boot-2.5.4.jar!/:2.5.4]
   ... 13 common frames omitted
   Caused by: org.springframework.jdbc.BadSqlGrammarException: ConnectionCallback; bad SQL grammar []; nested exception is org.postgresql.util.PSQLException: Unable to find server array type for provided name DOUBLE.
   at org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator.doTranslate(SQLErrorCodeSQLExceptionTranslator.java:239) ~[spring-jdbc-5.3.9.jar!/:5.3.9]
   at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:70) ~[spring-jdbc-5.3.9.jar!/:5.3.9]
   at org.springframework.jdbc.core.JdbcTemplate.translateException(JdbcTemplate.java:1541) ~[spring-jdbc-5.3.9.jar!/:5.3.9]
   at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:342) ~[spring-jdbc-5.3.9.jar!/:5.3.9]
   at org.springframework.data.jdbc.core.convert.DefaultJdbcTypeFactory.createArray(DefaultJdbcTypeFactory.java:60) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.convert.BasicJdbcConverter.writeJdbcValue(BasicJdbcConverter.java:321) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy.addConvertedValue(DefaultDataAccessStrategy.java:560) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy.addConvertedPropertyValue(DefaultDataAccessStrategy.java:548) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy.lambda$getParameterSource$2(DefaultDataAccessStrategy.java:462) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.mapping.model.BasicPersistentEntity.doWithProperties(BasicPersistentEntity.java:360) ~[spring-data-commons-2.5.4.jar!/:2.5.4]
   at org.springframework.data.mapping.PersistentEntity.doWithAll(PersistentEntity.java:255) ~[spring-data-commons-2.5.4.jar!/:2.5.4]
   at org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy.getParameterSource(DefaultDataAccessStrategy.java:440) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.convert.DefaultDataAccessStrategy.update(DefaultDataAccessStrategy.java:166) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.JdbcAggregateChangeExecutionContext.updateWithoutVersion(JdbcAggregateChangeExecutionContext.java:367) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.JdbcAggregateChangeExecutionContext.executeUpdateRoot(JdbcAggregateChangeExecutionContext.java:115) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.data.jdbc.core.AggregateChangeExecutor.execute(AggregateChangeExecutor.java:70) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   ... 57 common frames omitted
   Caused by: org.postgresql.util.PSQLException: Unable to find server array type for provided name DOUBLE.
   at org.postgresql.jdbc.PgConnection.createArrayOf(PgConnection.java:1379) ~[postgresql-42.2.23.jar!/:42.2.23]
   at org.postgresql.jdbc.PgConnection.createArrayOf(PgConnection.java:1399) ~[postgresql-42.2.23.jar!/:42.2.23]
   at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
   at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
   at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
   at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
   at org.springframework.jdbc.core.JdbcTemplate$CloseSuppressingInvocationHandler.invoke(JdbcTemplate.java:1614) ~[spring-jdbc-5.3.9.jar!/:5.3.9]
   at com.sun.proxy.$Proxy47.createArrayOf(Unknown Source) ~[na:na]
   at org.springframework.data.jdbc.core.convert.DefaultJdbcTypeFactory.lambda$createArray$1(DefaultJdbcTypeFactory.java:60) ~[spring-data-jdbc-2.2.4.jar!/:2.2.4]
   at org.springframework.jdbc.core.JdbcTemplate.execute(JdbcTemplate.java:334) ~[spring-jdbc-5.3.9.jar!/:5.3.9]
   ... 69 common frames omitted
   ```
