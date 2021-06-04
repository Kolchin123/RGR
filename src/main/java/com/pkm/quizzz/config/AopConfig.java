package com.pkm.quizzz.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.pkm.quizzz.service")
public class AopConfig {
 //включаем поддержку аннотации @Aspect, для поддержки сквозной функциональности
}
