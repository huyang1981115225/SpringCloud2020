package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**自定义Ribbon的配置类
 * 注意： 这个配置类不能放在 @ComponentScan 所扫描的当前包下及子包下，
 *        否则我们自定义的这个配置类就会被所有的Ribbon客户端所共享，达不到特殊化的目的了！！！
 *
 * @SpringBootApplication 注解里面就包含了@ComponentScan，所以需要单独建个包！！！
 *
 * @author huyang
 * @date 2020/5/16 19:10
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule(){
        return new RandomRule(); // 定义为随机
    }
}
