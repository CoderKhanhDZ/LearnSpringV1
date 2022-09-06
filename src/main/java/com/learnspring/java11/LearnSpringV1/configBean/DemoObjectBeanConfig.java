package com.learnspring.java11.LearnSpringV1.configBean;

import com.learnspring.java11.LearnSpringV1.model.DemoObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoObjectBeanConfig {

    @Bean
    public DemoObject createDemoObject(){

        DemoObject demoObject = new DemoObject();

        demoObject.setName("demo default");
        demoObject.setAge(0);

        return demoObject;
    }
}
