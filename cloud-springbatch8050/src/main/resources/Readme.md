# 一、Spring Boot对Batch框架的支持

### 1、Spring Batch框架的组成部分

1.  JobRepository：用来注册Job容器，设置数据库相关属性。 
2.  JobLauncher：用来启动Job的接口。
3.  Job：我们要实际执行的任务，包含一个或多个 
4.  Step：即步骤，包括：ItemReader->ItemProcessor->ItemWriter 
5.  ItemReader：用来读取数据，做实体类与数据字段之间的映射。比如读取csv文件中的人员数据，之后对应实体person的字段做mapper 
6.  ItemProcessor：用来处理数据的接口，同时可以做数据校验（设置校验器，使用JSR-303(hibernate-validator)注解），比如将中文性别男/女，转为M/F。同时校验年龄字段是否符合要求等 
7.  ItemWriter：用来输出数据的接口，设置数据库源。编写预处理SQL插入语句 

**注意： 以上七个组成部分，只需要在配置类中逐一注册即可，同时配置类需要开启@EnableBatchProcessing注解** 